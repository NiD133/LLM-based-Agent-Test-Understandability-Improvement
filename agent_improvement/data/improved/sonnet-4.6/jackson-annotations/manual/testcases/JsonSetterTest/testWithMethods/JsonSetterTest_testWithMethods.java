package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonSetterTest_testWithMethods extends AnnotationTestUtil {

    private final JsonSetter.Value EMPTY = JsonSetter.Value.empty();

    @Test
    public void testWithMethods() {
        // Passing null to withContentNulls normalizes it to DEFAULT, so EMPTY is returned unchanged
        JsonSetter.Value nullContentResult = EMPTY.withContentNulls(null);
        assertSame(EMPTY, nullContentResult);

        // Setting contentNulls to FAIL produces a new value with the updated setting
        JsonSetter.Value withContentFail = nullContentResult.withContentNulls(Nulls.FAIL);
        assertEquals(Nulls.FAIL, withContentFail.getContentNulls());

        // withContentNulls is idempotent: calling with the same value returns the same instance
        assertSame(withContentFail, withContentFail.withContentNulls(Nulls.FAIL));

        // withValueNulls creates a new distinct value; the original and new value are not equal
        JsonSetter.Value withValueSkip = withContentFail.withValueNulls(Nulls.SKIP);
        assertEquals(Nulls.SKIP, withValueSkip.getValueNulls());
        assertFalse(withContentFail.equals(withValueSkip));
        assertFalse(withValueSkip.equals(withContentFail));

        // withValueNulls(null, null) resets both valueNulls and contentNulls to DEFAULT
        JsonSetter.Value allDefaults = withValueSkip.withValueNulls(null, null);
        assertEquals(Nulls.DEFAULT, allDefaults.getContentNulls());
        assertEquals(Nulls.DEFAULT, allDefaults.getValueNulls());

        // withValueNulls(null, null) on an all-default value is idempotent
        assertSame(allDefaults, allDefaults.withValueNulls(null, null));

        // withOverrides applies non-DEFAULT settings from the override value;
        // result equals the override but is a distinct instance
        JsonSetter.Value merged = allDefaults.withOverrides(withValueSkip);
        assertNotSame(withValueSkip, merged);
        assertEquals(merged, withValueSkip);
        assertEquals(withValueSkip, merged);
    }
}
