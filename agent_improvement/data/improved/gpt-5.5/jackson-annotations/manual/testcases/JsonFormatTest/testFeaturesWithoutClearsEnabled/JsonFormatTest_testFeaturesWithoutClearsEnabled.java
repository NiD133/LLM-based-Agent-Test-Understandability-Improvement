package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonFormatTest_testFeaturesWithoutClearsEnabled extends AnnotationTestUtil {

    private final JsonFormat.Value emptyFormatValue = JsonFormat.Value.empty();

    @Test
    void testFeaturesWithoutClearsEnabled() {
        Feature targetFeature = Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY;

        JsonFormat.Features featuresWithExplicitDisable = JsonFormat.Features.empty()
                .with(targetFeature)
                .without(targetFeature);

        assertEquals(Boolean.FALSE, featuresWithExplicitDisable.get(targetFeature));
    }
}
