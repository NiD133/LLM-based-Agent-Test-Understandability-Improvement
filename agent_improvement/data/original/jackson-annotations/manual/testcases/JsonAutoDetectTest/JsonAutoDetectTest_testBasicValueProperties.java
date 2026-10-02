package com.fasterxml.jackson.annotation;

import java.lang.reflect.Member;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonAutoDetectTest_testBasicValueProperties extends AnnotationTestUtil {

    private final static JsonAutoDetect.Value NO_OVERRIDES = JsonAutoDetect.Value.noOverrides();

    private final static JsonAutoDetect.Value DEFAULTS = JsonAutoDetect.Value.defaultVisibility();

    @Test
    public void testBasicValueProperties() {
        JsonAutoDetect.Value v = JsonAutoDetect.Value.DEFAULT;
        assertEquals(JsonAutoDetect.class, v.valueFor());
        // and then standard method override basics...
        int x = v.hashCode();
        if (x == 0) {
            // not guaranteed in theory but...
            fail();
        }
        assertTrue(v.equals(v));
        // mostly to ensure no NPE or class cast exception:
        assertFalse(v.equals(null));
        assertFalse(v.equals("foo"));
    }
}
