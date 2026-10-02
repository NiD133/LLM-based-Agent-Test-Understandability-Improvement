package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JsonSetterTest_testWithMethods extends AnnotationTestUtil {

    private final JsonSetter.Value defaultSetterConfig = JsonSetter.Value.empty();

    @Test
    public void testWithMethods() {
        JsonSetter.Value unchangedDefault = defaultSetterConfig.withContentNulls(null);
        assertSame(defaultSetterConfig, unchangedDefault);

        JsonSetter.Value failOnContentNulls = unchangedDefault.withContentNulls(Nulls.FAIL);
        assertEquals(Nulls.FAIL, failOnContentNulls.getContentNulls());
        assertSame(failOnContentNulls, failOnContentNulls.withContentNulls(Nulls.FAIL));

        JsonSetter.Value skipValueNullsAndFailContentNulls = failOnContentNulls.withValueNulls(Nulls.SKIP);
        assertEquals(Nulls.SKIP, skipValueNullsAndFailContentNulls.getValueNulls());
        assertFalse(failOnContentNulls.equals(skipValueNullsAndFailContentNulls));
        assertFalse(skipValueNullsAndFailContentNulls.equals(failOnContentNulls));

        JsonSetter.Value explicitDefaults = skipValueNullsAndFailContentNulls.withValueNulls(null, null);
        assertEquals(Nulls.DEFAULT, explicitDefaults.getContentNulls());
        assertEquals(Nulls.DEFAULT, explicitDefaults.getValueNulls());
        assertSame(explicitDefaults, explicitDefaults.withValueNulls(null, null));

        JsonSetter.Value mergedWithNonDefaultOverrides = explicitDefaults.withOverrides(skipValueNullsAndFailContentNulls);
        assertNotSame(skipValueNullsAndFailContentNulls, mergedWithNonDefaultOverrides);
        assertEquals(mergedWithNonDefaultOverrides, skipValueNullsAndFailContentNulls);
        assertEquals(skipValueNullsAndFailContentNulls, mergedWithNonDefaultOverrides);
    }
}
