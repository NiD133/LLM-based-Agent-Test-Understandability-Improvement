package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.junit.jupiter.api.Test;
import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_RADIX;
import static org.junit.jupiter.api.Assertions.*;

public class JsonFormatTest_testMultiMerge extends AnnotationTestUtil {

    private final JsonFormat.Value EMPTY = JsonFormat.Value.empty();

    @Test
    public void testMultiMerge() {
        // not parsed, usage varies
        final String TEST_PATTERN = "format-string";
        JsonFormat.Value format2 = JsonFormat.Value.forPattern(TEST_PATTERN);
        JsonFormat.Value format3 = JsonFormat.Value.forLeniency(Boolean.FALSE);
        JsonFormat.Value merged = JsonFormat.Value.mergeAll(EMPTY, format2, format3);
        assertEquals(TEST_PATTERN, merged.getPattern());
        assertEquals(Boolean.FALSE, merged.getLenient());
    }
}
