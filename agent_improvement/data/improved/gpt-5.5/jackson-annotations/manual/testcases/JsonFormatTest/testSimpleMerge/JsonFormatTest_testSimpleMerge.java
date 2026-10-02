package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonFormatTest_testSimpleMerge extends AnnotationTestUtil {

    private static final String TEST_PATTERN = "format-string";
    private static final JsonFormat.Shape TEST_SHAPE = JsonFormat.Shape.NUMBER;

    private final JsonFormat.Value EMPTY = JsonFormat.Value.empty();

    @Test
    public void testSimpleMerge() {
        assertEmptyFormatValue(EMPTY);

        JsonFormat.Value valueWithPattern = JsonFormat.Value.forPattern(TEST_PATTERN);
        assertOnlyPatternConfigured(valueWithPattern);

        JsonFormat.Value merged = valueWithPattern.withOverrides(EMPTY);
        assertOnlyPatternConfigured(merged);

        assertSame(merged, merged.withOverrides(merged));

        merged = JsonFormat.Value.merge(EMPTY, valueWithPattern);
        assertOnlyPatternConfigured(merged);

        assertSame(merged, merged.withOverrides(null));

        JsonFormat.Value valueWithShape = JsonFormat.Value.forShape(TEST_SHAPE);
        merged = valueWithPattern.withOverrides(valueWithShape);
        assertPatternAndShapeConfigured(merged);

        merged = valueWithShape.withOverrides(valueWithPattern);
        assertPatternAndShapeConfigured(merged);
    }

    private void assertEmptyFormatValue(JsonFormat.Value value) {
        assertFalse(value.hasLocale());
        assertFalse(value.hasPattern());
        assertFalse(value.hasShape());
        assertFalse(value.hasTimeZone());
        assertNull(value.getLocale());
    }

    private void assertOnlyPatternConfigured(JsonFormat.Value value) {
        assertTrue(value.hasPattern());
        assertEquals(TEST_PATTERN, value.getPattern());
        assertFalse(value.hasLocale());
        assertFalse(value.hasShape());
        assertFalse(value.hasTimeZone());
    }

    private void assertPatternAndShapeConfigured(JsonFormat.Value value) {
        assertEquals(TEST_PATTERN, value.getPattern());
        assertFalse(value.hasLocale());
        assertEquals(TEST_SHAPE, value.getShape());
        assertFalse(value.hasTimeZone());
    }
}
