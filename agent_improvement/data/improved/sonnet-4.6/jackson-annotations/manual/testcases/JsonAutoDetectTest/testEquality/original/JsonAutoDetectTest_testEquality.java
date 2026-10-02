package com.fasterxml.jackson.annotation;

import java.lang.reflect.Member;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonAutoDetectTest_testEquality extends AnnotationTestUtil {

    private final static JsonAutoDetect.Value NO_OVERRIDES = JsonAutoDetect.Value.noOverrides();

    private final static JsonAutoDetect.Value DEFAULTS = JsonAutoDetect.Value.defaultVisibility();

    @Test
    public void testEquality() {
        assertEquals(NO_OVERRIDES, NO_OVERRIDES);
        assertEquals(DEFAULTS, DEFAULTS);
        assertFalse(DEFAULTS.equals(NO_OVERRIDES));
        assertFalse(NO_OVERRIDES.equals(DEFAULTS));
    }
}
