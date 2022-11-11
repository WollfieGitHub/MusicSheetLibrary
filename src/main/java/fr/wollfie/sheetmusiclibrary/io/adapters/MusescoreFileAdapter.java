package fr.wollfie.sheetmusiclibrary.io.adapters;

import fr.wollfie.sheetmusiclibrary.dto.*;
import fr.wollfie.sheetmusiclibrary.dto.files.MusescoreFile;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import fr.wollfie.sheetmusiclibrary.library.SheetMusicLibrary;
import fr.wollfie.sheetmusiclibrary.utils.Utils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static fr.wollfie.sheetmusiclibrary.io.adapters.XMLFileAdapter.*;

public class MusescoreFileAdapter {
    
    
    public static boolean decodeSheetMusic(File file) throws ParserConfigurationException, IOException, SAXException {
        // Step 1. Treat file as zip and unzip file
        // Step 2. Open the file with text and decode the XML 
        // Find attributes for tracks, author and title (Tracks is easy to find and decode)
        // Tracks are, for each instrument :
        // <Part>
        //  <Staff id="1"> <defaultClef>...</defaultClef> </Staff>
        //  <Staff id="2"> ...non if G clef... </Staff>
        //  <Instrument>...<Instrument>
        // </Part>
        //
        String title = "NOT FOUND";
        String composerName = "NOT FOUND";
        
        Logger.infof("==== Decoding new Sheet Music ====");

        XMLNode root = XMLNode.getRootFrom(file);
        XMLNode score = root.getFirstChildWith("Score").orElse(null);
        if (score == null) { return false; }
        
        List<XMLNode> parts = score.getAllChildrenWith("Part");
        if (parts.isEmpty()) { return false; }
        
        XMLNode info = score.getFirstChildWith("Staff").map(thenFirstChildWith("VBox"))
                .flatMap(Function.identity()).orElse(null);
        if (info == null) { return false; }
        
        XMLNode titleContainer = info.getParentOfValue("Title").orElse(null);
        if (titleContainer != null) {
            title = titleContainer.getFirstChildWith("text")
                    .map(XMLNode::getValue)
                    .map(Utils::sanitized)
                    .orElse(null);
            Logger.debugf("Title found");
        }

        XMLNode composerContainer = info.getParentOfValue("Composer").orElse(null);
        if (composerContainer != null) {
            composerName = composerContainer.getFirstChildWith("text")
                    .map(XMLNode::getValue)
                    .map(Utils::sanitized)
                    .map(name -> name.replaceFirst("by ", ""))
                    .map(name -> name.replaceFirst("By ", ""))
                    .orElse(null);
            Logger.debugf("Composer found");
        }

        List<Track> tracks = new ArrayList<>();
        for (XMLNode part : parts) 
        {
            List<XMLNode> staffs = part.getAllChildrenWith("Staff");
            if (staffs.isEmpty()) { return false; }
            
            List<Clef> clefs = staffs.stream().map(
                    staff -> staff.getFirstChildWith("defaultClef")
                            .map(XMLNode::getValue)
                            .map(Clef::fromEnglishName)
                            .orElse(Clef.TREBLE)
            ).toList();
            
            String instrumentName = part.getFirstChildWith("trackName")
                    .map(XMLNode::getValue)
                    .orElse(null);
            Logger.debugf("Instrument found");

            Optional<Instrument> instrument = SheetMusicLibrary.searchFor(instrumentName, MetadataType.Instrument, 1)
                    .stream()
                    .map(m -> (Instrument)m)
                    .findFirst();

            if (instrument.isEmpty()) {
                Logger.debugf("Instrument not found : \"%s\"", instrumentName);
                return false;
            }            
            for (Clef clef: clefs) {
                tracks.add(new Track(new MetadataRef<>(instrument.get()), clef));
                Logger.infof("%s | %s", instrumentName, clef);
            }
        }
        Optional<Artist> artist = SheetMusicLibrary.searchFor(composerName, MetadataType.Artist, 1)
                .stream()
                .map(m -> (Artist)m)
                .findFirst();

        if (artist.isEmpty() && composerName != null) {
            String[] composerNames = composerName.split(" ");
            Optional<String> composerLastName = composerNames.length > 1 
                    ? Optional.of(composerNames[composerNames.length-1])
                    : Optional.empty();
            
            String composerFirstName = Arrays.stream(composerNames)
                    .limit(composerNames.length-1)
                    .collect(Collectors.joining(" "));
            
            artist = Optional.of(new Artist(
                    composerFirstName,
                    composerLastName,
                    0,
                    Optional.empty()
            ));
            SheetMusicLibrary.tryInsert(artist.get());
        }

        Logger.infof("Title = \"%s\"\n", title);
        Logger.infof("Composer = \"%s\"", composerName);
        SheetMusicLibrary.tryInsert(new SheetMusic(
                title,
                artist.get(),
                tracks,
                null,
                null,
                new MusescoreFile(file)
        ));
        return true;
    }
}
