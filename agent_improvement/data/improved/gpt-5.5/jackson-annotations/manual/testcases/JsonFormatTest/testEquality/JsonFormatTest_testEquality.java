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
        assertTrue(EMPTY.equals(EMPTY));
        assertTrue(new JsonFormat.Value().equals(new JsonFormat.Value()));

        JsonFormat.Value booleanShape = JsonFormat.Value.forShape(Shape.BOOLEAN);
        JsonFormat.Value sameBooleanShape = JsonFormat.Value.forShape(Shape.BOOLEAN);
        JsonFormat.Value scalarShape = JsonFormat.Value.forShape(Shape.SCALAR);

        assertTrue(booleanShape.equals(sameBooleanShape));
        assertTrue(sameBooleanShape.equals(booleanShape));

        assertFalse(booleanShape.equals(scalarShape));
        assertFalse(scalarShape.equals(booleanShape));
        assertFalse(sameBooleanShape.equals(scalarShape));
        assertFalse(scalarShape.equals(sameBooleanShape));

        // not strictly guaranteed but...
        assertFalse(booleanShape.hashCode() == scalarShape.hashCode());

        // then let's converge
        assertEquals(booleanShape, scalarShape.withShape(Shape.BOOLEAN));
        assertFalse(booleanShape.equals(booleanShape.withPattern("ZBC")));
        assertFalse(booleanShape.equals(booleanShape.withFeature(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)));
        assertFalse(booleanShape.equals(booleanShape.withoutFeature(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)));
    }
}
