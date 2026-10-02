package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.junit.jupiter.api.Test;
import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_RADIX;
import static org.junit.jupiter.api.Assertions.*;

public class JsonFormatTest_testSimpleMerge extends AnnotationTestUtil {

    private final JsonFormat.Value EMPTY = JsonFormat.Value.empty();

    @Test
    public void testSimpleMerge() {
        // Start with an empty instance
        assertFalse(EMPTY.hasLocale());
        assertFalse(EMPTY.hasPattern());
        assertFalse(EMPTY.hasShape());
        assertFalse(EMPTY.hasTimeZone());
        assertNull(EMPTY.getLocale());
        // then with a non-empty one
        // not parsed, usage varies
        final String TEST_PATTERN = "format-string";
        JsonFormat.Value v = JsonFormat.Value.forPattern(TEST_PATTERN);
        assertTrue(v.hasPattern());
        assertEquals(TEST_PATTERN, v.getPattern());
        assertFalse(v.hasLocale());
        assertFalse(v.hasShape());
        assertFalse(v.hasTimeZone());
        // and ensure nothing overridden with empty
        JsonFormat.Value merged = v.withOverrides(EMPTY);
        assertEquals(TEST_PATTERN, merged.getPattern());
        assertFalse(merged.hasLocale());
        assertFalse(merged.hasShape());
        assertFalse(merged.hasTimeZone());
        // minor optimization: overriding with itself has no effect
        assertSame(merged, merged.withOverrides(merged));
        // but that empty is overridden
        merged = JsonFormat.Value.merge(EMPTY, v);
        assertEquals(TEST_PATTERN, merged.getPattern());
        assertFalse(merged.hasLocale());
        assertFalse(merged.hasShape());
        assertFalse(merged.hasTimeZone());
        // also some shortcuts:
        assertSame(merged, merged.withOverrides(null));
        // then with some other combinations
        final JsonFormat.Shape TEST_SHAPE = JsonFormat.Shape.NUMBER;
        JsonFormat.Value v2 = JsonFormat.Value.forShape(TEST_SHAPE);
        merged = v.withOverrides(v2);
        assertEquals(TEST_PATTERN, merged.getPattern());
        assertFalse(merged.hasLocale());
        assertEquals(TEST_SHAPE, merged.getShape());
        assertFalse(merged.hasTimeZone());
        merged = v2.withOverrides(v);
        assertEquals(TEST_PATTERN, merged.getPattern());
        assertFalse(merged.hasLocale());
        assertEquals(TEST_SHAPE, merged.getShape());
        assertFalse(merged.hasTimeZone());
    }
}
