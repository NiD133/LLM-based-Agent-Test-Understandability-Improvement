package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonFormat.Features;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies that enabling a feature with {@link Features#with} overrides a
 * prior {@link Features#without} call for that same feature.
 */
public class JsonFormatTest_testFeaturesWithClearsDisabled extends AnnotationTestUtil {

    private static final Feature SINGLE_VALUE_AS_ARRAY = Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY;

    @Test
    void testFeaturesWithClearsDisabled() {
        // Disable the feature, then re-enable it; the later with() should win.
        Features features = Features.empty()
                .without(SINGLE_VALUE_AS_ARRAY)
                .with(SINGLE_VALUE_AS_ARRAY);

        assertEquals(Boolean.TRUE, features.get(SINGLE_VALUE_AS_ARRAY),
                "with() after without() on the same feature should leave it enabled");
    }
}
