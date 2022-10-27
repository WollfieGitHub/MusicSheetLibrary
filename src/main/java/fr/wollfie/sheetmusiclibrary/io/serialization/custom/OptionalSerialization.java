package fr.wollfie.sheetmusiclibrary.io.serialization.custom;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Optional;

public class OptionalSerialization {
    
    private OptionalSerialization() {}
    
    public static class OptionalSerializer extends JsonSerializer<Optional>  {

        @Override
        public void serialize(Optional value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeStartObject();
            gen.writeObjectField("value", value.isPresent() ? value.get() : null);
            gen.writeBooleanField("present", value.isPresent());
            gen.writeEndObject();
        }
        
    }
    
    public static class OptionalDeserializer extends JsonDeserializer<Optional<?>> implements ContextualDeserializer {

        private JavaType valueType;

        @Override
        public Optional<?> deserialize(JsonParser p, DeserializationContext ctx) throws IOException, JacksonException {
            JsonNode node = p.getCodec().readTree(p);
            boolean isPresent = node.get("present").asBoolean();
            return isPresent ? Optional.of(p.getCodec().treeToValue(node.get("value"), valueType.getRawClass())) : Optional.empty();
        }

        @Override
        public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
            if (property == null) { //  context is generic
                OptionalDeserializer parser = new OptionalDeserializer();
                parser.valueType = ctxt.getContextualType().containedType(0);
                return parser;
            } else {  //  property is generic
                JavaType wrapperType = property.getType();
                JavaType valueType = wrapperType.containedType(0);
                OptionalDeserializer parser = new OptionalDeserializer();
                parser.valueType = valueType;
                return parser;
            }
        }
    }
}
