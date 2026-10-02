package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies that {@link JsonFormat.Value#toString()} renders all of the value's
 * fields in the documented {@code JsonFormat.Value(...)} format, including the
 * fields that were left at their defaults.
 */
public class JsonFormatTest_testToString extends AnnotationTestUtil {

    @Test
    public void testToString() {
        // A Value built only from a Shape: every other field stays at its default
        // (empty pattern, no lenient/locale/timezone, no features, default radix).
        JsonFormat.Value shapeOnly = JsonFormat.Value.forShape(JsonFormat.Shape.STRING);
        assertEquals(
                "JsonFormat.Value(pattern=,shape=STRING,lenient=null,locale=null,timezone=null,features=EMPTY,radix=-1)",
                shapeOnly.toString());

        // A Value built only from a pattern: shape falls back to the default ANY.
        JsonFormat.Value patternOnly = JsonFormat.Value.forPattern("[.]");
        assertEquals(
                "JsonFormat.Value(pattern=[.],shape=ANY,lenient=null,locale=null,timezone=null,features=EMPTY,radix=-1)",
                patternOnly.toString());
    }
}
