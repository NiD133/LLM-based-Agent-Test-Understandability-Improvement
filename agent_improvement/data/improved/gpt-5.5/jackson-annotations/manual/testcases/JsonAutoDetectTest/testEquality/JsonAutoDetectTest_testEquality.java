package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class JsonAutoDetectTest_testEquality extends AnnotationTestUtil {

    private static final JsonAutoDetect.Value NO_VISIBILITY_OVERRIDES =
            JsonAutoDetect.Value.noOverrides();
    private static final JsonAutoDetect.Value DEFAULT_VISIBILITY =
            JsonAutoDetect.Value.defaultVisibility();

    @Test
    public void testEquality() {
        assertEquals(NO_VISIBILITY_OVERRIDES, NO_VISIBILITY_OVERRIDES);
        assertEquals(DEFAULT_VISIBILITY, DEFAULT_VISIBILITY);

        assertFalse(DEFAULT_VISIBILITY.equals(NO_VISIBILITY_OVERRIDES));
        assertFalse(NO_VISIBILITY_OVERRIDES.equals(DEFAULT_VISIBILITY));
    }
}
