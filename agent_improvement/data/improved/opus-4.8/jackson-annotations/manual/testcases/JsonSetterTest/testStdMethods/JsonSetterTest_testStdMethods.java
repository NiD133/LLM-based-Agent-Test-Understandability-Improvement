package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Verifies the standard {@link Object} method overrides
 * ({@code toString}, {@code hashCode} and {@code equals}) of the
 * default/"empty" {@link JsonSetter.Value} instance.
 */
public class JsonSetterTest_testStdMethods extends AnnotationTestUtil {

    /** The default instance: both value-nulls and content-nulls use {@code Nulls.DEFAULT}. */
    private final JsonSetter.Value emptyValue = JsonSetter.Value.empty();

    @Test
    public void toStringShowsDefaultNullHandling() {
        assertEquals(
                "JsonSetter.Value(valueNulls=DEFAULT,contentNulls=DEFAULT)",
                emptyValue.toString());
    }

    @Test
    public void hashCodeIsNonZero() {
        // No fixed/required value, but it must not collapse to 0.
        assertNotEquals(0, emptyValue.hashCode());
    }

    @Test
    public void equalsIsReflexive() {
        assertEquals(emptyValue, emptyValue);
    }

    @Test
    public void notEqualToNull() {
        assertFalse(emptyValue.equals(null));
    }

    @Test
    public void notEqualToUnrelatedType() {
        assertFalse(emptyValue.equals("xyz"));
    }
}
