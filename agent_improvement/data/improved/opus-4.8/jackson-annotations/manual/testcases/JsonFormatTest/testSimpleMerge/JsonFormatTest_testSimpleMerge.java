package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonFormat.Value;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies how {@link JsonFormat.Value#withOverrides} and
 * {@link JsonFormat.Value#merge} combine two format values, where the
 * "overrides" value supplies any setting it actually defines and the base
 * value fills in the rest.
 */
public class JsonFormatTest_testSimpleMerge extends AnnotationTestUtil {

    /** A format value with no settings at all. */
    private final Value EMPTY = Value.empty();

    /** Pattern string used as the single non-default setting under test. */
    private static final String TEST_PATTERN = "format-string";

    @Test
    public void testSimpleMerge() {
        // The empty value should report that nothing has been configured.
        assertFalse(EMPTY.hasLocale());
        assertFalse(EMPTY.hasPattern());
        assertFalse(EMPTY.hasShape());
        assertFalse(EMPTY.hasTimeZone());
        assertNull(EMPTY.getLocale());

        // A value created from a pattern should carry only that pattern.
        Value patternOnly = Value.forPattern(TEST_PATTERN);
        assertTrue(patternOnly.hasPattern());
        assertEquals(TEST_PATTERN, patternOnly.getPattern());
        assertFalse(patternOnly.hasLocale());
        assertFalse(patternOnly.hasShape());
        assertFalse(patternOnly.hasTimeZone());

        // Overriding with an empty value changes nothing: pattern is kept.
        Value merged = patternOnly.withOverrides(EMPTY);
        assertEquals(TEST_PATTERN, merged.getPattern());
        assertFalse(merged.hasLocale());
        assertFalse(merged.hasShape());
        assertFalse(merged.hasTimeZone());

        // Optimization: overriding a value with itself returns that same instance.
        assertSame(merged, merged.withOverrides(merged));

        // Merging empty (base) with the pattern (overrides) yields the pattern.
        merged = Value.merge(EMPTY, patternOnly);
        assertEquals(TEST_PATTERN, merged.getPattern());
        assertFalse(merged.hasLocale());
        assertFalse(merged.hasShape());
        assertFalse(merged.hasTimeZone());

        // Optimization: overriding with null returns the same instance.
        assertSame(merged, merged.withOverrides(null));

        // Combining a pattern value and a shape value keeps both settings,
        // regardless of which one acts as base and which one as overrides.
        Value shapeOnly = Value.forShape(Shape.NUMBER);

        merged = patternOnly.withOverrides(shapeOnly);
        assertEquals(TEST_PATTERN, merged.getPattern());
        assertFalse(merged.hasLocale());
        assertEquals(Shape.NUMBER, merged.getShape());
        assertFalse(merged.hasTimeZone());

        merged = shapeOnly.withOverrides(patternOnly);
        assertEquals(TEST_PATTERN, merged.getPattern());
        assertFalse(merged.hasLocale());
        assertEquals(Shape.NUMBER, merged.getShape());
        assertFalse(merged.hasTimeZone());
    }
}
