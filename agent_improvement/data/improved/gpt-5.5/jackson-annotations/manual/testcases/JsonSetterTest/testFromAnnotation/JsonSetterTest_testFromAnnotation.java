package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class JsonSetterTest_testFromAnnotation extends AnnotationTestUtil {

    private static final JsonSetter.Value EMPTY_SETTER_VALUE = JsonSetter.Value.empty();

    static class Bogus {
        @JsonSetter(nulls = Nulls.FAIL, contentNulls = Nulls.SKIP)
        public String field;
    }

    @Test
    public void testFromAnnotation() throws Exception {
        assertSame(EMPTY_SETTER_VALUE, JsonSetter.Value.from(null));

        JsonSetter annotation = Bogus.class.getField("field").getAnnotation(JsonSetter.class);
        JsonSetter.Value value = JsonSetter.Value.from(annotation);

        assertEquals(Nulls.FAIL, value.getValueNulls());
        assertEquals(Nulls.SKIP, value.getContentNulls());

        byte[] serializedValue = jdkSerialize(value);
        JsonSetter.Value deserializedValue = jdkDeserialize(serializedValue);

        assertEquals(value, deserializedValue);
    }
}
