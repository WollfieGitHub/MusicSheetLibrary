package fr.wollfie.sheetmusiclibrary.io.serialization.custom;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import fr.wollfie.sheetmusiclibrary.utils.Tuple;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.util.List;

public class TupleSerialization {

    private TupleSerialization() {}

    public static class TupleSerializer extends JsonSerializer<Tuple> {

        @Override
        public void serialize(Tuple value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeStartObject();
            gen.writeObjectField("left", value.left());
            gen.writeObjectField("right", value.right());
            gen.writeEndObject();
        }
    }

    public static class TupleDeserializer extends JsonDeserializer<Tuple<?, ?>> implements ContextualDeserializer {

        private JavaType leftType;
        private JavaType rightType;

        public TupleDeserializer() {
        }

        public TupleDeserializer(JavaType leftType, JavaType rightType) {
            this.leftType = leftType;
            this.rightType = rightType;
        }

        @Override
        public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
            JavaType type = ctxt.getContextualType();
            return new TupleDeserializer(
                    type.containedType(0),
                    type.containedType(1)
            );
        }

        @Override
        public Tuple<?, ?> deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            return new Tuple<>(
                    p.getCodec().treeToValue(node.get("left"), leftType.getRawClass()),
                    p.getCodec().treeToValue(node.get("right"), rightType.getRawClass())
                );
        }
    }
}
