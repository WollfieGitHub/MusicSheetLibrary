package fr.wollfie.sheetmusiclibrary.io.serialization.custom;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public class ObservableListSerialization {
    
    private ObservableListSerialization() {}

    public static class ObservableListSerializer extends JsonSerializer<ObservableList> {

        @Override
        public void serialize(ObservableList value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeStartObject();
            gen.writeObjectField("list", value.stream().toList());
            gen.writeEndObject();
        }
    }

    public static class ObservableListDeserializer extends JsonDeserializer<ObservableList<?>> implements ContextualDeserializer {

        private JavaType valueType;

        @Override
        public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
            if (property == null) { //  context is generic
                ObservableListSerialization.ObservableListDeserializer parser = new ObservableListSerialization.ObservableListDeserializer();
                parser.valueType = ctxt.getContextualType().containedType(0);
                return parser;
            } else {  //  property is generic
                JavaType wrapperType = property.getType();
                JavaType valueType = wrapperType.containedType(0);
                ObservableListSerialization.ObservableListDeserializer parser = new ObservableListSerialization.ObservableListDeserializer();
                parser.valueType = valueType;
                return parser;
            }
        }
        
        @Override
        public ObservableList<?> deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            return new SimpleListProperty<>(FXCollections.observableList((List<?>) p.getCodec().treeToValue(
                    node.get("list"), valueType.getRawClass()
            )));
        }
    }
}
