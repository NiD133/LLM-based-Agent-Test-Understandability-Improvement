package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonAutoDetectTest_testBasicValueProperties extends AnnotationTestUtil {

    @Test
    public void testBasicValueProperties() {
        JsonAutoDetect.Value v = JsonAutoDetect.Value.DEFAULT;

        assertEquals(JsonAutoDetect.class, v.valueFor());

        assertNotEquals(0, v.hashCode());

        assertTrue(v.equals(v));
        assertFalse(v.equals(null));
        assertFalse(v.equals("foo"));
    }
}
