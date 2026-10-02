package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonFormatTest_testCaseInsensitiveValues extends AnnotationTestUtil {

    @Test
    public void testCaseInsensitiveValues() {
        JsonFormat.Value defaultFormat = JsonFormat.Value.empty();
        assertNull(defaultFormat.getFeature(Feature.ACCEPT_CASE_INSENSITIVE_VALUES));

        JsonFormat.Value caseInsensitiveFormat =
                defaultFormat.withFeature(Feature.ACCEPT_CASE_INSENSITIVE_VALUES);
        assertTrue(caseInsensitiveFormat.getFeature(Feature.ACCEPT_CASE_INSENSITIVE_VALUES));

        JsonFormat.Value caseSensitiveFormat =
                defaultFormat.withoutFeature(Feature.ACCEPT_CASE_INSENSITIVE_VALUES);
        assertFalse(caseSensitiveFormat.getFeature(Feature.ACCEPT_CASE_INSENSITIVE_VALUES));
    }
}
