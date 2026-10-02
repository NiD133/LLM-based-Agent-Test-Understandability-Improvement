package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonFormatTest_testSimpleMerge extends AnnotationTestUtil {

    private static final JsonFormat.Value EMPTY = JsonFormat.Value.empty();
    private static final String PATTERN = "format-string";
    private static final Shape SHAPE = Shape.NUMBER;

    @Test
    public void testEmptyValueHasNoProperties() {
        assertFalse(EMPTY.hasLocale());
        assertFalse(EMPTY.hasPattern());
        assertFalse(EMPTY.hasShape());
        assertFalse(EMPTY.hasTimeZone());
        assertNull(EMPTY.getLocale());
    }

    @Test
    public void testPatternValueHasOnlyPatternSet() {
        JsonFormat.Value withPattern = JsonFormat.Value.forPattern(PATTERN);
        assertTrue(withPattern.hasPattern());
        assertEquals(PATTERN, withPattern.getPattern());
        assertFalse(withPattern.hasLocale());
        assertFalse(withPattern.hasShape());
        assertFalse(withPattern.hasTimeZone());
    }

    @Test
    public void testOverridingWithEmptyPreservesOriginalPattern() {
        JsonFormat.Value withPattern = JsonFormat.Value.forPattern(PATTERN);
        JsonFormat.Value merged = withPattern.withOverrides(EMPTY);
        assertEquals(PATTERN, merged.getPattern());
        assertFalse(merged.hasLocale());
        assertFalse(merged.hasShape());
        assertFalse(merged.hasTimeZone());
    }

    @Test
    public void testSelfOverrideReturnsSameInstance() {
        JsonFormat.Value withPattern = JsonFormat.Value.forPattern(PATTERN);
        JsonFormat.Value merged = withPattern.withOverrides(EMPTY);
        assertSame(merged, merged.withOverrides(merged));
    }

    @Test
    public void testStaticMergeWithEmptyBaseUsesOverrideValues() {
        JsonFormat.Value withPattern = JsonFormat.Value.forPattern(PATTERN);
        JsonFormat.Value merged = JsonFormat.Value.merge(EMPTY, withPattern);
        assertEquals(PATTERN, merged.getPattern());
        assertFalse(merged.hasLocale());
        assertFalse(merged.hasShape());
        assertFalse(merged.hasTimeZone());
    }

    @Test
    public void testNullOverrideReturnsSameInstance() {
        JsonFormat.Value withPattern = JsonFormat.Value.forPattern(PATTERN);
        JsonFormat.Value merged = JsonFormat.Value.merge(EMPTY, withPattern);
        assertSame(merged, merged.withOverrides(null));
    }

    @Test
    public void testMergingPatternAndShapePreservesBoth() {
        JsonFormat.Value withPattern = JsonFormat.Value.forPattern(PATTERN);
        JsonFormat.Value withShape = JsonFormat.Value.forShape(SHAPE);

        JsonFormat.Value patternOverridesShape = withPattern.withOverrides(withShape);
        assertEquals(PATTERN, patternOverridesShape.getPattern());
        assertFalse(patternOverridesShape.hasLocale());
        assertEquals(SHAPE, patternOverridesShape.getShape());
        assertFalse(patternOverridesShape.hasTimeZone());

        JsonFormat.Value shapeOverridesPattern = withShape.withOverrides(withPattern);
        assertEquals(PATTERN, shapeOverridesPattern.getPattern());
        assertFalse(shapeOverridesPattern.hasLocale());
        assertEquals(SHAPE, shapeOverridesPattern.getShape());
        assertFalse(shapeOverridesPattern.hasTimeZone());
    }
}
