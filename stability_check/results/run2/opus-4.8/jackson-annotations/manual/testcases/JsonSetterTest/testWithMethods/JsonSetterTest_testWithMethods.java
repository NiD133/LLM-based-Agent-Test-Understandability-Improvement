package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies the {@code withXxx} mutant-factory methods of {@link JsonSetter.Value}:
 * they should return the same instance when nothing changes, produce a new
 * instance when a setting actually changes, and honour equality/merge semantics.
 */
public class JsonSetterTest_testWithMethods extends AnnotationTestUtil {

    private final JsonSetter.Value EMPTY = JsonSetter.Value.empty();

    @Test
    public void testWithMethods() {
        // A no-op change (null -> DEFAULT, already DEFAULT) returns the same EMPTY instance.
        JsonSetter.Value unchanged = EMPTY.withContentNulls(null);
        assertSame(EMPTY, unchanged);

        // Setting content nulls to FAIL creates a value that reports FAIL,
        // and re-applying the same value is a no-op that returns the same instance.
        JsonSetter.Value withContentFail = unchanged.withContentNulls(Nulls.FAIL);
        assertEquals(Nulls.FAIL, withContentFail.getContentNulls());
        assertSame(withContentFail, withContentFail.withContentNulls(Nulls.FAIL));

        // Adding a value-nulls setting produces a distinct, non-equal instance.
        JsonSetter.Value withValueSkip = withContentFail.withValueNulls(Nulls.SKIP);
        assertEquals(Nulls.SKIP, withValueSkip.getValueNulls());
        assertFalse(withContentFail.equals(withValueSkip));
        assertFalse(withValueSkip.equals(withContentFail));

        // Passing (null, null) resets both settings back to DEFAULT;
        // reapplying the same defaults is a no-op that returns the same instance.
        JsonSetter.Value bothDefault = withValueSkip.withValueNulls(null, null);
        assertEquals(Nulls.DEFAULT, bothDefault.getContentNulls());
        assertEquals(Nulls.DEFAULT, bothDefault.getValueNulls());
        assertSame(bothDefault, bothDefault.withValueNulls(null, null));

        // Merging in overrides yields a new instance equal to those overrides.
        JsonSetter.Value merged = bothDefault.withOverrides(withValueSkip);
        assertNotSame(withValueSkip, merged);
        assertEquals(merged, withValueSkip);
        assertEquals(withValueSkip, merged);
    }
}
