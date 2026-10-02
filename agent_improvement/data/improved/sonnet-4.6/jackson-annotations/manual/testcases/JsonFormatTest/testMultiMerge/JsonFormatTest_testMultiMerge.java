package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that {@link JsonFormat.Value#mergeAll} correctly combines multiple
 * {@code Value} instances, with later entries overriding earlier ones for
 * non-empty/non-null fields.
 */
public class JsonFormatTest_testMultiMerge extends AnnotationTestUtil {

    // Represents a baseline with no settings configured
    private final JsonFormat.Value EMPTY = JsonFormat.Value.empty();

    @Test
    public void testMultiMerge() {
        // A format string used for date/time pattern configuration (not parsed here)
        final String DATE_FORMAT_PATTERN = "format-string";

        // Two partial Value instances, each carrying one piece of configuration
        JsonFormat.Value withPattern  = JsonFormat.Value.forPattern(DATE_FORMAT_PATTERN);
        JsonFormat.Value withLeniency = JsonFormat.Value.forLeniency(Boolean.FALSE);

        // mergeAll applies values left-to-right; later non-empty settings win
        JsonFormat.Value merged = JsonFormat.Value.mergeAll(EMPTY, withPattern, withLeniency);

        // The merged result should carry the pattern from withPattern …
        assertEquals(DATE_FORMAT_PATTERN, merged.getPattern());
        // … and the strict (non-lenient) flag from withLeniency
        assertEquals(Boolean.FALSE, merged.getLenient());
    }
}
