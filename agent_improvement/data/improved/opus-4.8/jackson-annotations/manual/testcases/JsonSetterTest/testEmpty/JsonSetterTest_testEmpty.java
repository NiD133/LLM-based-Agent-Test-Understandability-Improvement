package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Verifies the state of the "empty" {@link JsonSetter.Value} returned by
 * {@link JsonSetter.Value#empty()} — the instance that represents the absence
 * of any explicit null-handling configuration.
 */
public class JsonSetterTest_testEmpty extends AnnotationTestUtil {

    /** The shared default instance whose properties are under test. */
    private final JsonSetter.Value emptyValue = JsonSetter.Value.empty();

    @Test
    public void emptyValueUsesDefaultNullHandling() {
        // Both value- and content-level null handling fall back to the global default.
        assertEquals(Nulls.DEFAULT, emptyValue.getValueNulls());
        assertEquals(Nulls.DEFAULT, emptyValue.getContentNulls());

        // The value describes configuration for the @JsonSetter annotation.
        assertEquals(JsonSetter.class, emptyValue.valueFor());

        // Since both settings are DEFAULT, the "non-default" accessors report null.
        assertNull(emptyValue.nonDefaultValueNulls());
        assertNull(emptyValue.nonDefaultContentNulls());
    }
}
