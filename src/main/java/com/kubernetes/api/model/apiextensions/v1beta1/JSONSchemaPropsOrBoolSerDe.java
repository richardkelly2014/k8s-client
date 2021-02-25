package com.kubernetes.api.model.apiextensions.v1beta1;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class JSONSchemaPropsOrBoolSerDe {
    private JSONSchemaPropsOrBoolSerDe() {
    }

    public static class Serializer extends JsonSerializer<JSONSchemaPropsOrBool> {
        @Override
        public void serialize(JSONSchemaPropsOrBool jsonSchemaPropsOrBool,
                              JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            if (jsonSchemaPropsOrBool.getSchema() != null) {
                jsonGenerator.writeObject(jsonSchemaPropsOrBool.getSchema());
            } else {
                jsonGenerator.writeBoolean(jsonSchemaPropsOrBool.getAllows());
            }
        }
    }

    public static class Deserializer extends JsonDeserializer<JSONSchemaPropsOrBool> {

        @Override
        public JSONSchemaPropsOrBool deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            JSONSchemaPropsOrBool jsonSchemaPropsOrBool = new JSONSchemaPropsOrBool();

            //JSONSchemaPropsOrBoolBuilder builder = new JSONSchemaPropsOrBoolBuilder();
            if (jsonParser.isExpectedStartObjectToken()) {
                jsonSchemaPropsOrBool.setSchema(jsonParser.readValueAs(JSONSchemaProps.class));
                jsonSchemaPropsOrBool.setAllows(true);
//                builder.withSchema(
//                        jsonParser.readValueAs(JSONSchemaProps.class));
//                builder.withAllows(true);
            } else {
                jsonSchemaPropsOrBool.setAllows(jsonParser.getBooleanValue());
                //builder.withAllows(jsonParser.getBooleanValue());
            }
            //return builder.build();
            return jsonSchemaPropsOrBool;
        }
    }
}
