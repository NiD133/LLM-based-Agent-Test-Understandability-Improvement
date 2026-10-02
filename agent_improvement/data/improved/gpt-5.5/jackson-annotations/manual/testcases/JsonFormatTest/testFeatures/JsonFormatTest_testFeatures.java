package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonFormatTest_testFeatures extends AnnotationTestUtil {

    private static final Feature ACCEPT_SINGLE_VALUE_AS_ARRAY =
            Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY;
    private static final Feature WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS =
            Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS;

    private final JsonFormat.Value EMPTY = JsonFormat.Value.empty();

    @Test
    public void testFeatures() {
        JsonFormat.Features defaults = JsonFormat.Features.empty();
        JsonFormat.Features customized = defaults
                .with(ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .without(WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);

        assertTrue(defaults.equals(defaults));
        assertFalse(defaults.equals(customized));
        assertFalse(defaults.equals(null));
        assertFalse(defaults.equals("foo"));

        assertFeatureUnset(defaults, ACCEPT_SINGLE_VALUE_AS_ARRAY);
        assertFeatureEnabled(customized, ACCEPT_SINGLE_VALUE_AS_ARRAY);
        assertFeatureUnset(defaults, WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);
        assertFeatureDisabled(customized, WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);

        JsonFormat.Features merged = defaults.withOverrides(customized);
        assertFeatureEnabled(merged, ACCEPT_SINGLE_VALUE_AS_ARRAY);
        assertFeatureDisabled(merged, WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);

        JsonFormat.Features switchedValues = JsonFormat.Features.construct(
                new Feature[] { WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS },
                new Feature[] { ACCEPT_SINGLE_VALUE_AS_ARRAY });

        assertFeatureDisabled(switchedValues, ACCEPT_SINGLE_VALUE_AS_ARRAY);
        assertFeatureEnabled(switchedValues, WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);
    }

    private void assertFeatureUnset(JsonFormat.Features features, Feature feature) {
        assertNull(features.get(feature));
    }

    private void assertFeatureEnabled(JsonFormat.Features features, Feature feature) {
        assertEquals(Boolean.TRUE, features.get(feature));
    }

    private void assertFeatureDisabled(JsonFormat.Features features, Feature feature) {
        assertEquals(Boolean.FALSE, features.get(feature));
    }
}
