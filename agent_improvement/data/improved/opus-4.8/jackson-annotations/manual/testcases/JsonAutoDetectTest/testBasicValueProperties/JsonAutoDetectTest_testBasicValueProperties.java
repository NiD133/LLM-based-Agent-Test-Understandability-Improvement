package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies the basic {@code Object}-method contract of {@link JsonAutoDetect.Value},
 * exercised through the shared {@link JsonAutoDetect.Value#DEFAULT} instance:
 * <ul>
 *   <li>{@code valueFor()} reports the annotation type it describes,</li>
 *   <li>{@code hashCode()} produces a (practically) non-zero value,</li>
 *   <li>{@code equals(...)} is reflexive and null/other-type safe.</li>
 * </ul>
 */
public class JsonAutoDetectTest_testBasicValueProperties extends AnnotationTestUtil {

    @Test
    public void testBasicValueProperties() {
        JsonAutoDetect.Value defaultValue = JsonAutoDetect.Value.DEFAULT;

        // valueFor() must identify the annotation this Value represents.
        assertEquals(JsonAutoDetect.class, defaultValue.valueFor());

        // hashCode() is not contractually required to be non-zero, but for the
        // DEFAULT instance it should be in practice; a zero value flags a regression.
        assertNotEquals(0, defaultValue.hashCode(), "hashCode() unexpectedly returned 0");

        // equals(...) must be reflexive...
        assertTrue(defaultValue.equals(defaultValue), "Value must equal itself");

        // ...and must reject null and unrelated types without throwing
        // (e.g. NullPointerException or ClassCastException).
        assertFalse(defaultValue.equals(null), "Value must not equal null");
        assertFalse(defaultValue.equals("foo"), "Value must not equal an unrelated type");
    }
}
