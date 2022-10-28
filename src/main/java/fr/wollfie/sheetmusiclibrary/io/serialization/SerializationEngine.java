package fr.wollfie.sheetmusiclibrary.io.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;
import fr.wollfie.sheetmusiclibrary.dto.MetadataRef;
import fr.wollfie.sheetmusiclibrary.io.serialization.custom.*;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * https://www.baeldung.com/jackson-object-mapper-tutorial
 */
public class SerializationEngine {
    
    private SerializationEngine() {}
    
    private static final SimpleModule MODULE;
    private static final DefaultPrettyPrinter PRETTY_PRINTER;
    static {
        MODULE = new SimpleModule()
                // Optional
                .addDeserializer(Optional.class, new OptionalSerialization.OptionalDeserializer())
                .addSerializer(Optional.class, new OptionalSerialization.OptionalSerializer())
                // Color
                .addDeserializer(Color.class, new ColorSerialization.ColorDeserializer())
                .addSerializer(Color.class, new ColorSerialization.ColorSerializer())
                // FontIcon
                .addDeserializer(FontIcon.class, new FontIconSerialization.FontIconDeserializer())
                .addSerializer(FontIcon.class, new FontIconSerialization.FontIconSerializer())
                // MetadataRef
                .addDeserializer(MetadataRef.class, new MetadataRefSerialization.MetadataRefDeserializer())
                .addSerializer(MetadataRef.class, new MetadataRefSerialization.MetadataRefSerializer())
                // ObservableList
                .addDeserializer(ObservableList.class, new ObservableListSerialization.ObservableListDeserializer())
                .addSerializer(ObservableList.class, new ObservableListSerialization.ObservableListSerializer())
        
        ;

        DefaultPrettyPrinter.Indenter indenter =
                new DefaultIndenter("    ", DefaultIndenter.SYS_LF);
        PRETTY_PRINTER = new DefaultPrettyPrinter();
        PRETTY_PRINTER.indentObjectsWith(indenter);
        PRETTY_PRINTER.indentArraysWith(indenter);
    }

    /**
     * Load the given metadataObj object from the given file in json format
     * @param file The file to load the object from
     * @param outputClass The class of the object to load
     * @throws IOException if the specified file is not found or low level IO error occurred
     */
    public static <R extends JsonSerializable> R loadFrom(File file, Class<R> outputClass) throws IOException {
        if (!file.exists()) { return null; }
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(MODULE);
        return mapper.readValue(file, outputClass);
    }

    /**
     * Load the given metadataObj objects from the given file in json format
     * @param file The file to load the object from
     * @param outputClass The class of the object to load
     * @return A list of metadata objects
     * @throws IOException if the specified file is not found or low level IO error occurred
     */
    public static <R extends JsonSerializable> List<R> loadAllFrom(File file, Class<R> outputClass) throws IOException {
        if (!file.exists()) { return Collections.emptyList(); }
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(MODULE);
        List<R> result = new ArrayList<>();
        try (JsonParser parser = mapper.createParser(file)) {
            mapper.readValues(parser, outputClass).readAll(result);
        }
        return result;
    }

    /**
     * Saves the given object into the specified file
     * @param file The file to save the object to
     * @param metadataObj The object to save
     * @throws IOException if the specified file is not found or low level IO error occurred
     */
    public static <R extends JsonSerializable> void saveTo(File file, R metadataObj) throws IOException {
        ObjectWriter mapper = new ObjectMapper().registerModule(MODULE).writer(PRETTY_PRINTER);
        mapper.writeValue(file, metadataObj);
    }

    /**
     * Saves the given objects into the specified file
     * @param file The file to save the object to
     * @param metadataObj The object to save
     * @throws IOException if the specified file is not found or low level IO error occurred
     */
    public static <R extends JsonSerializable> void saveAllTo(File file, List<R> metadataObj) throws IOException {
        ObjectWriter mapper = new ObjectMapper().registerModule(MODULE).writer(PRETTY_PRINTER);
        try (SequenceWriter out = mapper.writeValues(file)) {
            out.writeAll(metadataObj);
        }
    }
    
}
