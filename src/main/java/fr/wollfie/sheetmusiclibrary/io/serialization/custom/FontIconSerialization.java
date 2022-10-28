package fr.wollfie.sheetmusiclibrary.io.serialization.custom;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

public class FontIconSerialization {

    private FontIconSerialization() {}

    public static class FontIconSerializer extends JsonSerializer<FontIcon> {

        @Override
        public void serialize(FontIcon value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            gen.writeStringField("code", value.getIconLiteral());
            gen.writeObjectField("color", value.getIconColor());
            gen.writeNumberField("size", value.getIconSize());
            gen.writeEndObject();
        }
    }

    public static class FontIconDeserializer extends JsonDeserializer<FontIcon> {

        @Override
        public FontIcon deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            JsonNode node = p.getCodec().readTree(p);
            FontIcon icon = new FontIcon(node.get("code").asText());
            icon.setIconSize(node.get("size").asInt());
            icon.setIconColor(p.getCodec().treeToValue(node.get("color"), Color.class));
            return icon;
        }
    }
}
