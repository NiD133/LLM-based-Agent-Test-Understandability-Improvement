package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonFormatTest_testFeaturesWithoutClearsEnabled extends AnnotationTestUtil {

    @Test
    void testFeaturesWithoutClearsEnabled() {
        // Enable a feature, then immediately disable the same feature.
        // The disable (without) should take precedence, resulting in Boolean.FALSE.
        JsonFormat.Features featuresWithEnabled = JsonFormat.Features.empty()
                .with(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        JsonFormat.Features featuresAfterDisable = featuresWithEnabled
                .without(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        assertEquals(Boolean.FALSE, featuresAfterDisable.get(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY),
                "Feature should be explicitly disabled (FALSE) after calling without(), not absent (null)");
    }
}
