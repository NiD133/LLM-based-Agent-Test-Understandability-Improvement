package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class JsonFormatTest_testFromAnnotation extends AnnotationTestUtil {

    private final JsonFormat.Value emptyFormatValue = JsonFormat.Value.empty();

    @JsonFormat(pattern = "xyz", shape = JsonFormat.Shape.BOOLEAN, timezone = "bogus")
    private static class Bogus { }

    @Test
    public void testFromAnnotation() {
        assertSame(emptyFormatValue, JsonFormat.Value.from(null));

        JsonFormat annotation = Bogus.class.getAnnotation(JsonFormat.class);
        JsonFormat.Value formatValue = JsonFormat.Value.from(annotation);

        assertEquals("xyz", formatValue.getPattern());
        assertEquals(JsonFormat.Shape.BOOLEAN, formatValue.getShape());
        assertEquals("bogus", formatValue.timeZoneAsString());

        byte[] serializedValue = jdkSerialize(formatValue);
        JsonFormat.Value deserializedValue = jdkDeserialize(serializedValue);
        assertEquals(formatValue, deserializedValue);
    }
}
