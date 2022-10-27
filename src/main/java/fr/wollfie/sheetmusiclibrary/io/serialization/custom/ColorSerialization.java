package fr.wollfie.sheetmusiclibrary.io.serialization.custom;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import javafx.scene.paint.Color;
import javafx.util.converter.LocalDateStringConverter;

import java.io.IOException;
import java.security.KeyManagementException;

public class ColorSerialization {
    
    private ColorSerialization() {}
    
    public static class ColorSerializer extends JsonSerializer<Color> {

        @Override
        public void serialize(Color value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            gen.writeNumberField("r", value.getRed());
            gen.writeNumberField("g", value.getGreen());
            gen.writeNumberField("b", value.getBlue());
            gen.writeNumberField("a", value.getOpacity());
            gen.writeEndObject();
        }
    }
    
    public static class ColorDeserializer extends JsonDeserializer<Color> {

        @Override
        public Color deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            JsonNode node = p.getCodec().readTree(p);
            return new Color(
                    node.get("r").asDouble(),
                    node.get("g").asDouble(),
                    node.get("b").asDouble(),
                    node.get("a").asDouble()
            );
        }
    }
}
