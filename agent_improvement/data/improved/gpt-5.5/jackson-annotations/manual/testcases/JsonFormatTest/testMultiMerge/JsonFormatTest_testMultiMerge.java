package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonFormatTest_testMultiMerge extends AnnotationTestUtil {

    private static final JsonFormat.Value EMPTY_FORMAT = JsonFormat.Value.empty();
    private static final String TEST_PATTERN = "format-string";

    @Test
    public void testMultiMerge() {
        JsonFormat.Value formatWithPattern = JsonFormat.Value.forPattern(TEST_PATTERN);
        JsonFormat.Value formatWithStrictLeniency = JsonFormat.Value.forLeniency(Boolean.FALSE);

        JsonFormat.Value merged = JsonFormat.Value.mergeAll(
                EMPTY_FORMAT,
                formatWithPattern,
                formatWithStrictLeniency);

        assertEquals(TEST_PATTERN, merged.getPattern());
        assertEquals(Boolean.FALSE, merged.getLenient());
    }
}
