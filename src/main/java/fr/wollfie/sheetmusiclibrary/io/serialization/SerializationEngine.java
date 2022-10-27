package fr.wollfie.sheetmusiclibrary.io.serialization;

import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;
import fr.wollfie.sheetmusiclibrary.dto.Metadata;
import fr.wollfie.sheetmusiclibrary.io.metadata.MetadataFile;
import fr.wollfie.sheetmusiclibrary.io.serialization.custom.ColorSerialization;
import fr.wollfie.sheetmusiclibrary.io.serialization.custom.OptionalSerialization;
import javafx.scene.paint.Color;

import java.io.File;

import java.io.IOException;
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
    public static <R extends Metadata> R loadFrom(File file, Class<R> outputClass) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(MODULE);
        return mapper.readValue(file, outputClass);
    }

    /**
     * Saves the given object into the specified file
     * @param file The file to save the object to
     * @param metadataObj The object to save
     * @throws IOException if the specified file is not found or low level IO error occurred
     */
    public static <R extends Metadata> void saveTo(File file, R metadataObj) throws IOException {
        ObjectWriter mapper = new ObjectMapper().registerModule(MODULE).writer(PRETTY_PRINTER);
        mapper.writeValue(file, metadataObj);
    }
    
}
