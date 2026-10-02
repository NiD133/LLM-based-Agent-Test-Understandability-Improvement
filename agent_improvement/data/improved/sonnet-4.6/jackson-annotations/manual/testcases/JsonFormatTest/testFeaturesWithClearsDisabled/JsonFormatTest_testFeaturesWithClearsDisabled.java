package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonFormatTest_testFeaturesWithClearsDisabled extends AnnotationTestUtil {

    @Test
    void testFeaturesWithClearsDisabled() {
        // Disabling a feature and then re-enabling it should result in the feature being enabled.
        // This verifies that with() removes the disabled flag set by without() for the same feature.
        JsonFormat.Features featuresWithDisabled =
                JsonFormat.Features.empty().without(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        JsonFormat.Features featuresReEnabled =
                featuresWithDisabled.with(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        assertEquals(Boolean.TRUE, featuresReEnabled.get(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY));
    }
}
