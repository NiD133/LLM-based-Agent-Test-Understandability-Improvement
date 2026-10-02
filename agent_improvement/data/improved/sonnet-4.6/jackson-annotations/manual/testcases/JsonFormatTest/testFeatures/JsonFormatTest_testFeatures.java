package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonFormatTest_testFeatures extends AnnotationTestUtil {

    @Test
    public void testEmptyFeaturesReturnsNullForAnyFeature() {
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();

        assertNull(emptyFeatures.get(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY));
        assertNull(emptyFeatures.get(Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS));
    }

    @Test
    public void testWithEnablesFeatureAndWithoutDisablesFeature() {
        JsonFormat.Features featuresWithOverrides = JsonFormat.Features.empty()
                .with(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .without(Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);

        assertEquals(Boolean.TRUE, featuresWithOverrides.get(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY));
        assertEquals(Boolean.FALSE, featuresWithOverrides.get(Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS));
    }

    @Test
    public void testFeaturesEquality() {
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        JsonFormat.Features featuresWithOverrides = emptyFeatures
                .with(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .without(Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);

        assertTrue(emptyFeatures.equals(emptyFeatures));
        assertFalse(emptyFeatures.equals(featuresWithOverrides));
        assertFalse(emptyFeatures.equals(null));
        assertFalse(emptyFeatures.equals("foo"));
    }

    @Test
    public void testWithOverridesAppliesEnabledAndDisabledFromOverride() {
        JsonFormat.Features base = JsonFormat.Features.empty();
        JsonFormat.Features overrides = base
                .with(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .without(Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);

        JsonFormat.Features merged = base.withOverrides(overrides);

        assertEquals(Boolean.TRUE, merged.get(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY));
        assertEquals(Boolean.FALSE, merged.get(Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS));
    }

    @Test
    public void testConstructFromEnabledAndDisabledArraysWithSwitchedValues() {
        JsonFormat.Features features = JsonFormat.Features.construct(
                new Feature[] { Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS }, // enabled
                new Feature[] { Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY });         // disabled

        assertEquals(Boolean.FALSE, features.get(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY));
        assertEquals(Boolean.TRUE, features.get(Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS));
    }
}
