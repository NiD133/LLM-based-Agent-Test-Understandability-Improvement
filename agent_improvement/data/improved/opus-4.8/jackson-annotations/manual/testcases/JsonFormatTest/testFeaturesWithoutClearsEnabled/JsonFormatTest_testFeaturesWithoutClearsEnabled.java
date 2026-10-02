package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies that on a {@link JsonFormat.Features} set, calling {@code without(...)}
 * for a feature overrides a previous {@code with(...)} for that same feature,
 * leaving it explicitly disabled (i.e. {@code without} clears {@code with}).
 */
public class JsonFormatTest_testFeaturesWithoutClearsEnabled extends AnnotationTestUtil {

    @Test
    void testFeaturesWithoutClearsEnabled() {
        Feature feature = Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY;

        // Enable the feature, then disable it on the same Features set.
        JsonFormat.Features features = JsonFormat.Features.empty()
                .with(feature)
                .without(feature);

        // The final without(...) wins: the feature ends up explicitly disabled.
        assertEquals(Boolean.FALSE, features.get(feature));
    }
}
