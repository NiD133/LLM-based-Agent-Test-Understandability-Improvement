package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.junit.jupiter.api.Test;
import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_RADIX;
import static org.junit.jupiter.api.Assertions.*;

public class JsonFormatTest_testEquality extends AnnotationTestUtil {

    private final JsonFormat.Value EMPTY = JsonFormat.Value.empty();

    @Test
    public void testEquality() {
        // A value must equal itself (reflexive equality)
        assertTrue(EMPTY.equals(EMPTY));

        // Two independently constructed default Values should be equal
        assertTrue(new JsonFormat.Value().equals(new JsonFormat.Value()));

        // Values created with the same shape are equal; different shapes are not
        JsonFormat.Value booleanShape1 = JsonFormat.Value.forShape(Shape.BOOLEAN);
        JsonFormat.Value booleanShape2 = JsonFormat.Value.forShape(Shape.BOOLEAN);
        JsonFormat.Value scalarShape   = JsonFormat.Value.forShape(Shape.SCALAR);

        assertTrue(booleanShape1.equals(booleanShape2));
        assertTrue(booleanShape2.equals(booleanShape1));
        assertFalse(booleanShape1.equals(scalarShape));
        assertFalse(scalarShape.equals(booleanShape1));
        assertFalse(booleanShape2.equals(scalarShape));
        assertFalse(scalarShape.equals(booleanShape2));

        // Hash codes should differ for values with different shapes
        // (not guaranteed by contract, but expected for these distinct shapes)
        assertNotEquals(booleanShape1.hashCode(), scalarShape.hashCode());

        // Changing scalarShape's shape to BOOLEAN should make it equal to booleanShape1
        assertEquals(booleanShape1, scalarShape.withShape(Shape.BOOLEAN));

        // Changing pattern or feature flags on an otherwise-equal value breaks equality
        assertFalse(booleanShape1.equals(booleanShape1.withPattern("ZBC")));
        assertFalse(booleanShape1.equals(booleanShape1.withFeature(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)));
        assertFalse(booleanShape1.equals(booleanShape1.withoutFeature(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)));
    }
}
