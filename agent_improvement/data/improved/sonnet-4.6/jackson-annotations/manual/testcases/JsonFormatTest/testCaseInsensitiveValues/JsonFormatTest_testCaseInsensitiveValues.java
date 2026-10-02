package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonFormatTest_testCaseInsensitiveValues extends AnnotationTestUtil {

    @Test
    public void testCaseInsensitiveValues() {
        // An empty Value has no explicit feature setting: null means "unset, inherit default"
        JsonFormat.Value emptyValue = JsonFormat.Value.empty();
        assertNull(emptyValue.getFeature(Feature.ACCEPT_CASE_INSENSITIVE_VALUES));

        // withFeature explicitly enables the feature: getFeature returns true
        JsonFormat.Value caseInsensitiveValue = emptyValue.withFeature(Feature.ACCEPT_CASE_INSENSITIVE_VALUES);
        assertTrue(caseInsensitiveValue.getFeature(Feature.ACCEPT_CASE_INSENSITIVE_VALUES));

        // withoutFeature explicitly disables the feature: getFeature returns false (not null)
        JsonFormat.Value caseSensitiveValue = emptyValue.withoutFeature(Feature.ACCEPT_CASE_INSENSITIVE_VALUES);
        assertFalse(caseSensitiveValue.getFeature(Feature.ACCEPT_CASE_INSENSITIVE_VALUES));
    }
}
