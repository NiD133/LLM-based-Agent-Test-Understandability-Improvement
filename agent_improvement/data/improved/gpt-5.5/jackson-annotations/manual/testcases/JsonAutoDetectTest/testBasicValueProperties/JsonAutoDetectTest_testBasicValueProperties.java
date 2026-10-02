package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonAutoDetectTest_testBasicValueProperties extends AnnotationTestUtil {

    @Test
    public void testBasicValueProperties() {
        JsonAutoDetect.Value defaultVisibility = JsonAutoDetect.Value.DEFAULT;

        assertEquals(JsonAutoDetect.class, defaultVisibility.valueFor());

        int defaultHashCode = defaultVisibility.hashCode();
        assertNotEquals(0, defaultHashCode);

        assertTrue(defaultVisibility.equals(defaultVisibility));
        assertFalse(defaultVisibility.equals(null));
        assertFalse(defaultVisibility.equals("foo"));
    }
}
