package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

public class JsonIgnorePropertiesTest_testEquality extends AnnotationTestUtil {

    private static final JsonIgnoreProperties.Value EMPTY_VALUE =
            JsonIgnoreProperties.Value.empty();

    @Test
    public void testEquality() {
        assertEquals(EMPTY_VALUE, EMPTY_VALUE);

        // The empty value already has merge enabled, so enabling it again is a no-op.
        assertSame(EMPTY_VALUE, EMPTY_VALUE.withMerge());

        JsonIgnoreProperties.Value valueWithoutMerge = EMPTY_VALUE.withoutMerge();

        assertEquals(valueWithoutMerge, valueWithoutMerge);
        assertFalse(EMPTY_VALUE.equals(valueWithoutMerge));
        assertFalse(valueWithoutMerge.equals(EMPTY_VALUE));
    }
}
