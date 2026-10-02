package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies the behaviour of the {@code withXxx} "mutant factory" methods on
 * {@link JsonSetter.Value}. These methods are expected to be immutable-friendly:
 * when a change would not actually alter the state they return the very same
 * instance, and only otherwise do they produce a new value.
 */
public class JsonSetterTest_testWithMethods extends AnnotationTestUtil {

    private final JsonSetter.Value EMPTY = JsonSetter.Value.empty();

    @Test
    public void testWithMethods() {
        // A null content-nulls argument means "no change", so the empty
        // instance should be returned unchanged (same reference).
        JsonSetter.Value unchanged = EMPTY.withContentNulls(null);
        assertSame(EMPTY, unchanged);

        // Setting content-nulls to a concrete value produces a new value that
        // reports the requested content-nulls setting.
        JsonSetter.Value withContentFail = unchanged.withContentNulls(Nulls.FAIL);
        assertEquals(Nulls.FAIL, withContentFail.getContentNulls());
        // Re-applying the identical setting is a no-op and returns the same instance.
        assertSame(withContentFail, withContentFail.withContentNulls(Nulls.FAIL));

        // Setting value-nulls yields yet another distinct value; because its
        // value-nulls differs, it is not equal to the previous instance.
        JsonSetter.Value withValueSkip = withContentFail.withValueNulls(Nulls.SKIP);
        assertEquals(Nulls.SKIP, withValueSkip.getValueNulls());
        assertFalse(withContentFail.equals(withValueSkip));
        assertFalse(withValueSkip.equals(withContentFail));

        // Passing (null, null) resets both nulls settings back to DEFAULT.
        JsonSetter.Value resetToDefaults = withValueSkip.withValueNulls(null, null);
        assertEquals(Nulls.DEFAULT, resetToDefaults.getContentNulls());
        assertEquals(Nulls.DEFAULT, resetToDefaults.getValueNulls());
        // Resetting an already-default value is a no-op and returns the same instance.
        assertSame(resetToDefaults, resetToDefaults.withValueNulls(null, null));

        // Merging in overrides from withValueSkip creates a new (non-same)
        // instance that is logically equal to those overrides.
        JsonSetter.Value merged = resetToDefaults.withOverrides(withValueSkip);
        assertNotSame(withValueSkip, merged);
        assertEquals(merged, withValueSkip);
        assertEquals(withValueSkip, merged);
    }
}
