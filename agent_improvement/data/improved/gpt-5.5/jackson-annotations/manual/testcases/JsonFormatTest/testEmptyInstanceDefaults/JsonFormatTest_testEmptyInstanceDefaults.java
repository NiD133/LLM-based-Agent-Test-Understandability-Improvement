package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

public class JsonFormatTest_testEmptyInstanceDefaults extends AnnotationTestUtil {

    @Test
    public void testEmptyInstanceDefaults() {
        JsonFormat.Value empty = JsonFormat.Value.empty();

        assertNoFeatureOverrides(empty);
        assertNoExplicitFormatProperties(empty);
    }

    private void assertNoFeatureOverrides(JsonFormat.Value value) {
        for (Feature feature : Feature.values()) {
            assertNull(value.getFeature(feature));
        }
    }

    private void assertNoExplicitFormatProperties(JsonFormat.Value value) {
        assertFalse(value.hasLocale());
        assertFalse(value.hasPattern());
        assertFalse(value.hasShape());
        assertFalse(value.hasTimeZone());
        assertFalse(value.hasLenient());
        assertFalse(value.hasNonDefaultRadix());
        assertFalse(value.isLenient());
    }
}
