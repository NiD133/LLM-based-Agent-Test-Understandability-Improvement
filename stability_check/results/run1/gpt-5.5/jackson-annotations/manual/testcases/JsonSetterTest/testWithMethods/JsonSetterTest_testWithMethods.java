package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JsonSetterTest_testWithMethods extends AnnotationTestUtil {

    private final JsonSetter.Value emptySettings = JsonSetter.Value.empty();

    @Test
    public void testWithMethods() {
        JsonSetter.Value unchangedDefaults = emptySettings.withContentNulls(null);
        assertSame(emptySettings, unchangedDefaults);

        JsonSetter.Value failOnContentNulls = unchangedDefaults.withContentNulls(Nulls.FAIL);
        assertEquals(Nulls.FAIL, failOnContentNulls.getContentNulls());
        assertSame(failOnContentNulls, failOnContentNulls.withContentNulls(Nulls.FAIL));

        JsonSetter.Value skipOnValueNulls = failOnContentNulls.withValueNulls(Nulls.SKIP);
        assertEquals(Nulls.SKIP, skipOnValueNulls.getValueNulls());
        assertFalse(failOnContentNulls.equals(skipOnValueNulls));
        assertFalse(skipOnValueNulls.equals(failOnContentNulls));

        JsonSetter.Value resetToDefaults = skipOnValueNulls.withValueNulls(null, null);
        assertEquals(Nulls.DEFAULT, resetToDefaults.getContentNulls());
        assertEquals(Nulls.DEFAULT, resetToDefaults.getValueNulls());
        assertSame(resetToDefaults, resetToDefaults.withValueNulls(null, null));

        JsonSetter.Value mergedWithSpecificOverrides = resetToDefaults.withOverrides(skipOnValueNulls);
        assertNotSame(skipOnValueNulls, mergedWithSpecificOverrides);
        assertEquals(mergedWithSpecificOverrides, skipOnValueNulls);
        assertEquals(skipOnValueNulls, mergedWithSpecificOverrides);
    }
}
