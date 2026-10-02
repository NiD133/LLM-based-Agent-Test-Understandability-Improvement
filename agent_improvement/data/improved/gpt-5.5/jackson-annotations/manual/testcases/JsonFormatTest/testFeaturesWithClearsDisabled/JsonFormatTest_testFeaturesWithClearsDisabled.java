package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonFormatTest_testFeaturesWithClearsDisabled extends AnnotationTestUtil {

    private static final Feature SINGLE_VALUE_AS_ARRAY = Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY;

    @SuppressWarnings("unused")
    private final JsonFormat.Value emptyFormatValue = JsonFormat.Value.empty();

    @Test
    void testFeaturesWithClearsDisabled() {
        JsonFormat.Features disabledFeature = JsonFormat.Features.empty()
                .without(SINGLE_VALUE_AS_ARRAY);

        JsonFormat.Features reenabledFeature = disabledFeature
                .with(SINGLE_VALUE_AS_ARRAY);

        assertEquals(Boolean.TRUE, reenabledFeature.get(SINGLE_VALUE_AS_ARRAY));
    }
}
