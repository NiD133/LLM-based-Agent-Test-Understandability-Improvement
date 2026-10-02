package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies the immutable "mutant factory" behaviour of {@link JsonSetter.Value}:
 * the {@code withXxx} methods either return the same instance when nothing
 * changes, or produce a new instance carrying the updated null-handling settings.
 */
public class JsonSetterTest_testWithMethods extends AnnotationTestUtil {

    private final JsonSetter.Value EMPTY = JsonSetter.Value.empty();

    @Test
    public void testWithMethods() {
        // A null argument means "no change", so the empty instance is returned as-is.
        JsonSetter.Value unchanged = EMPTY.withContentNulls(null);
        assertSame(EMPTY, unchanged);

        // Setting a real contentNulls value produces a new instance with that value.
        JsonSetter.Value withFailContent = EMPTY.withContentNulls(Nulls.FAIL);
        assertEquals(Nulls.FAIL, withFailContent.getContentNulls());
        // Re-applying the same value is a no-op and returns the same instance.
        assertSame(withFailContent, withFailContent.withContentNulls(Nulls.FAIL));

        // Changing valueNulls yields yet another, distinct instance.
        JsonSetter.Value withSkipValue = withFailContent.withValueNulls(Nulls.SKIP);
        assertEquals(Nulls.SKIP, withSkipValue.getValueNulls());
        assertNotEquals(withFailContent, withSkipValue);
        assertNotEquals(withSkipValue, withFailContent);

        // Resetting both nulls to default (via null, null) gives the all-default instance.
        JsonSetter.Value allDefault = withSkipValue.withValueNulls(null, null);
        assertEquals(Nulls.DEFAULT, allDefault.getContentNulls());
        assertEquals(Nulls.DEFAULT, allDefault.getValueNulls());
        // Applying the same default reset again is a no-op.
        assertSame(allDefault, allDefault.withValueNulls(null, null));

        // Merging overrides into the all-default base adopts the override's settings,
        // producing a new instance that is equal to (but not the same as) the override.
        JsonSetter.Value merged = allDefault.withOverrides(withSkipValue);
        assertNotSame(withSkipValue, merged);
        assertEquals(merged, withSkipValue);
        assertEquals(withSkipValue, merged);
    }
}
