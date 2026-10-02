package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.junit.jupiter.api.Test;
import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_RADIX;
import static org.junit.jupiter.api.Assertions.*;

public class JsonFormatTest_testCaseInsensitiveValues extends AnnotationTestUtil {

    private final JsonFormat.Value EMPTY = JsonFormat.Value.empty();

    @Test
    public void testCaseInsensitiveValues() {
        JsonFormat.Value empty = JsonFormat.Value.empty();
        assertNull(empty.getFeature(Feature.ACCEPT_CASE_INSENSITIVE_VALUES));
        JsonFormat.Value insensitive = empty.withFeature(Feature.ACCEPT_CASE_INSENSITIVE_VALUES);
        assertTrue(insensitive.getFeature(Feature.ACCEPT_CASE_INSENSITIVE_VALUES));
        JsonFormat.Value sensitive = empty.withoutFeature(Feature.ACCEPT_CASE_INSENSITIVE_VALUES);
        assertFalse(sensitive.getFeature(Feature.ACCEPT_CASE_INSENSITIVE_VALUES));
    }
}
