package com.fasterxml.jackson.annotation;

import java.lang.reflect.Member;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonAutoDetectTest_testAnnotationProperties extends AnnotationTestUtil {

    private final static JsonAutoDetect.Value NO_OVERRIDES = JsonAutoDetect.Value.noOverrides();

    private final static JsonAutoDetect.Value DEFAULTS = JsonAutoDetect.Value.defaultVisibility();

    @Test
    public void testAnnotationProperties() throws Exception {
        Member m = Bogus.class.getField("value");
        assertTrue(JsonAutoDetect.Visibility.ANY.isVisible(m));
        assertFalse(JsonAutoDetect.Visibility.NONE.isVisible(m));
        assertTrue(JsonAutoDetect.Visibility.NON_PRIVATE.isVisible(m));
        assertTrue(JsonAutoDetect.Visibility.PUBLIC_ONLY.isVisible(m));
        assertTrue(JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC.isVisible(m));
        assertTrue(JsonAutoDetect.Visibility.NON_PRIVATE.isVisible(m));
        // forget why DEFAULT would give false but
        assertFalse(JsonAutoDetect.Visibility.DEFAULT.isVisible(m));
    }
}
