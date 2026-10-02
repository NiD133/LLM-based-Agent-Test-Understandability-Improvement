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
        JsonFormat.Value v1 = JsonFormat.Value.forShape(Shape.BOOLEAN);
        JsonFormat.Value v2 = JsonFormat.Value.forShape(Shape.BOOLEAN);
        JsonFormat.Value v3 = JsonFormat.Value.forShape(Shape.SCALAR);
        assertTrue(v1.equals(v2));
        assertTrue(v2.equals(v1));
        assertFalse(v1.equals(v3));
        assertFalse(v3.equals(v1));
        assertFalse(v2.equals(v3));
        assertFalse(v3.equals(v2));
        // not strictly guaranteed but...
        assertFalse(v1.hashCode() == v3.hashCode());
        // then let's converge
        assertEquals(v1, v3.withShape(Shape.BOOLEAN));
        assertFalse(v1.equals(v1.withPattern("ZBC")));
        assertFalse(v1.equals(v1.withFeature(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)));
        assertFalse(v1.equals(v1.withoutFeature(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)));
    }
}
