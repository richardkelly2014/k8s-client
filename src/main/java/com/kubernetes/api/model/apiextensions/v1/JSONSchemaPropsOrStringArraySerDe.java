package com.kubernetes.api.model.apiextensions.v1;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;

public class JSONSchemaPropsOrStringArraySerDe {
    private JSONSchemaPropsOrStringArraySerDe() {
    }

    public static class Serializer extends JsonSerializer<JSONSchemaPropsOrStringArray> {
        @Override
        public void serialize(JSONSchemaPropsOrStringArray jsonSchemaPropsOrStringArray,
                              JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            if (jsonSchemaPropsOrStringArray.getProperty() != null && !jsonSchemaPropsOrStringArray.getProperty().isEmpty()) {
                jsonGenerator.writeStartArray();
                for (String property : jsonSchemaPropsOrStringArray.getProperty()) {
                    jsonGenerator.writeObject(property);
                }
                jsonGenerator.writeEndArray();
            } else {
                jsonGenerator.writeObject(jsonSchemaPropsOrStringArray.getSchema());
            }
        }
    }

    public static class Deserializer extends JsonDeserializer<JSONSchemaPropsOrStringArray> {

        @Override
        public JSONSchemaPropsOrStringArray deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

            JSONSchemaPropsOrStringArray jsonSchemaPropsOrStringArray = new JSONSchemaPropsOrStringArray();

            //JSONSchemaPropsOrStringArrayBuilder builder = new JSONSchemaPropsOrStringArrayBuilder();
            if (jsonParser.isExpectedStartObjectToken()) {
                jsonSchemaPropsOrStringArray.setSchema(
                        jsonParser.readValueAs(JSONSchemaProps.class)
                );
//                builder.withSchema(
//                        jsonParser.readValueAs(JSONSchemaProps.class));
            } else if (jsonParser.isExpectedStartArrayToken()) {
                jsonSchemaPropsOrStringArray.setProperty(jsonParser.<List<String>>readValueAs(new TypeReference<List<String>>() {
                }));
//                builder.withProperty(jsonParser.<List<String>>readValueAs(new TypeReference<List<String>>() {
//                }));
            }
            //return builder.build();
            return jsonSchemaPropsOrStringArray;
        }
    }
}
