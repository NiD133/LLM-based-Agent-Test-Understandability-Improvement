package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Verifies {@link JsonSetter.Value#from(JsonSetter)}: it must translate the
 * {@code nulls} / {@code contentNulls} attributes of a {@link JsonSetter}
 * annotation into a {@link JsonSetter.Value}, and the resulting value must
 * survive a JDK serialization round-trip.
 */
public class JsonSetterTest_testFromAnnotation extends AnnotationTestUtil {

    /** Holder whose field carries a fully-specified {@link JsonSetter} annotation. */
    private static final class AnnotatedHolder {
        @JsonSetter(nulls = Nulls.FAIL, contentNulls = Nulls.SKIP)
        public int annotatedField;
    }

    private final JsonSetter.Value EMPTY = JsonSetter.Value.empty();

    @Test
    public void testFromAnnotation() throws Exception {
        // A null annotation maps to the shared EMPTY value (same instance).
        assertSame(EMPTY, JsonSetter.Value.from(null));

        // A real annotation is translated attribute-for-attribute.
        JsonSetter annotation = AnnotatedHolder.class
                .getField("annotatedField")
                .getAnnotation(JsonSetter.class);
        JsonSetter.Value value = JsonSetter.Value.from(annotation);
        assertEquals(Nulls.FAIL, value.getValueNulls());
        assertEquals(Nulls.SKIP, value.getContentNulls());

        // The value must stay equal to itself after a JDK serialization round-trip.
        byte[] serialized = jdkSerialize(value);
        JsonSetter.Value deserialized = jdkDeserialize(serialized);
        assertEquals(value, deserialized);
    }
}
