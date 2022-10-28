package fr.wollfie.sheetmusiclibrary.io.serialization.custom;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import fr.wollfie.sheetmusiclibrary.dto.MetadataRef;

import java.io.IOException;

public class MetadataRefSerialization {
    
    private MetadataRefSerialization() {}

    public static class MetadataRefSerializer extends JsonSerializer<MetadataRef> {

        @Override
        public void serialize(MetadataRef value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeStartObject();
            gen.writeObjectField("valueUId", value.valueUId);
            gen.writeEndObject();
        }
    }

    public static class MetadataRefDeserializer extends JsonDeserializer<MetadataRef<?>>  {
        
        @Override
        public MetadataRef<?> deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            return new MetadataRef<>(node.get("valueUId").asText());
        }
    }
}
