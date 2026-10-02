package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies that {@link JsonFormat.Value#mergeAll(JsonFormat.Value...)} combines
 * several {@code Value} instances into one, layering each later value's explicit
 * settings on top of the earlier ones.
 */
public class JsonFormatTest_testMultiMerge extends AnnotationTestUtil {

    @Test
    public void testMultiMerge() {
        final String PATTERN = "format-string"; // not parsed, usage varies

        // Three sources to merge: a base with no settings, then one contributing
        // a pattern, then one contributing a (non-lenient) leniency flag.
        JsonFormat.Value emptyBase = JsonFormat.Value.empty();
        JsonFormat.Value patternOnly = JsonFormat.Value.forPattern(PATTERN);
        JsonFormat.Value leniencyOnly = JsonFormat.Value.forLeniency(Boolean.FALSE);

        JsonFormat.Value merged = JsonFormat.Value.mergeAll(emptyBase, patternOnly, leniencyOnly);

        // The merged value should carry the contributions from each source.
        assertEquals(PATTERN, merged.getPattern());
        assertEquals(Boolean.FALSE, merged.getLenient());
    }
}
