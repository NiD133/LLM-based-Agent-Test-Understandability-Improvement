package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies the {@link JsonFormat.Value#equals(Object)} (and related
 * {@link JsonFormat.Value#hashCode()}) contract: two {@code Value}s are equal
 * only when all of their format settings match, and they stop being equal as
 * soon as any single setting (shape, pattern, or feature flag) differs.
 */
public class JsonFormatTest_testEquality extends AnnotationTestUtil {

    private final JsonFormat.Value EMPTY = JsonFormat.Value.empty();

    @Test
    public void testEquality() {
        // A value is always equal to itself, and two freshly-built defaults match.
        assertTrue(EMPTY.equals(EMPTY));
        assertTrue(new JsonFormat.Value().equals(new JsonFormat.Value()));

        // Two values built with the same shape are equal; a different shape is not.
        JsonFormat.Value booleanShape = JsonFormat.Value.forShape(Shape.BOOLEAN);
        JsonFormat.Value sameBooleanShape = JsonFormat.Value.forShape(Shape.BOOLEAN);
        JsonFormat.Value scalarShape = JsonFormat.Value.forShape(Shape.SCALAR);

        // Equality is symmetric for matching shapes...
        assertTrue(booleanShape.equals(sameBooleanShape));
        assertTrue(sameBooleanShape.equals(booleanShape));

        // ...and symmetric inequality for mismatched shapes.
        assertFalse(booleanShape.equals(scalarShape));
        assertFalse(scalarShape.equals(booleanShape));
        assertFalse(sameBooleanShape.equals(scalarShape));
        assertFalse(scalarShape.equals(sameBooleanShape));

        // Different shapes should (not strictly guaranteed, but expected) hash differently.
        assertFalse(booleanShape.hashCode() == scalarShape.hashCode());

        // Switching the SCALAR value's shape to BOOLEAN makes it equal to the BOOLEAN value.
        assertEquals(booleanShape, scalarShape.withShape(Shape.BOOLEAN));

        // Changing any single setting away from the original breaks equality.
        assertFalse(booleanShape.equals(booleanShape.withPattern("ZBC")));
        assertFalse(booleanShape.equals(booleanShape.withFeature(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)));
        assertFalse(booleanShape.equals(booleanShape.withoutFeature(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)));
    }
}
