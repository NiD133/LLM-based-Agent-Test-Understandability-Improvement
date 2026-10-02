package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.junit.jupiter.api.Test;
import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_RADIX;
import static org.junit.jupiter.api.Assertions.*;

public class JsonFormatTest_testEmptyInstanceDefaults extends AnnotationTestUtil {

    private final JsonFormat.Value EMPTY = JsonFormat.Value.empty();

    @Test
    public void testEmptyInstanceDefaults() {
        JsonFormat.Value empty = JsonFormat.Value.empty();
        for (Feature f : Feature.values()) {
            assertNull(empty.getFeature(f));
        }
        assertFalse(empty.hasLocale());
        assertFalse(empty.hasPattern());
        assertFalse(empty.hasShape());
        assertFalse(empty.hasTimeZone());
        assertFalse(empty.hasLenient());
        assertFalse(empty.hasNonDefaultRadix());
        assertFalse(empty.isLenient());
    }
}
