package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonSetterTest_testWithMethods extends AnnotationTestUtil {

    private final JsonSetter.Value EMPTY = JsonSetter.Value.empty();

    @Test
    public void withContentNulls_withNullArgument_returnsSameInstance() {
        JsonSetter.Value result = EMPTY.withContentNulls(null);
        assertSame(EMPTY, result);
    }

    @Test
    public void withContentNulls_withNewValue_updatesContentNulls() {
        JsonSetter.Value withFail = EMPTY.withContentNulls(Nulls.FAIL);
        assertEquals(Nulls.FAIL, withFail.getContentNulls());
    }

    @Test
    public void withContentNulls_withSameValue_returnsSameInstance() {
        JsonSetter.Value withFail = EMPTY.withContentNulls(Nulls.FAIL);
        assertSame(withFail, withFail.withContentNulls(Nulls.FAIL));
    }

    @Test
    public void withValueNulls_withNewValue_updatesValueNulls() {
        JsonSetter.Value base = EMPTY.withContentNulls(Nulls.FAIL);
        JsonSetter.Value withSkip = base.withValueNulls(Nulls.SKIP);
        assertEquals(Nulls.SKIP, withSkip.getValueNulls());
    }

    @Test
    public void withValueNulls_withDifferentValueNulls_producesInequalInstances() {
        JsonSetter.Value base = EMPTY.withContentNulls(Nulls.FAIL);
        JsonSetter.Value withSkip = base.withValueNulls(Nulls.SKIP);
        assertFalse(base.equals(withSkip));
        assertFalse(withSkip.equals(base));
    }

    @Test
    public void withValueNulls_withBothNullArgs_resetsBothToDefault() {
        JsonSetter.Value base = EMPTY.withContentNulls(Nulls.FAIL).withValueNulls(Nulls.SKIP);
        JsonSetter.Value resetToDefaults = base.withValueNulls(null, null);
        assertEquals(Nulls.DEFAULT, resetToDefaults.getContentNulls());
        assertEquals(Nulls.DEFAULT, resetToDefaults.getValueNulls());
    }

    @Test
    public void withValueNulls_withBothNullArgsOnDefaultInstance_returnsSameInstance() {
        JsonSetter.Value base = EMPTY.withContentNulls(Nulls.FAIL).withValueNulls(Nulls.SKIP);
        JsonSetter.Value resetToDefaults = base.withValueNulls(null, null);
        assertSame(resetToDefaults, resetToDefaults.withValueNulls(null, null));
    }

    @Test
    public void withOverrides_appliesOverrideValues_producedValueEqualsOverride() {
        JsonSetter.Value allDefaults = EMPTY.withContentNulls(Nulls.FAIL)
                .withValueNulls(Nulls.SKIP)
                .withValueNulls(null, null);
        JsonSetter.Value overrideValue = EMPTY.withContentNulls(Nulls.FAIL).withValueNulls(Nulls.SKIP);
        JsonSetter.Value merged = allDefaults.withOverrides(overrideValue);
        assertNotSame(overrideValue, merged);
        assertEquals(merged, overrideValue);
        assertEquals(overrideValue, merged);
    }
}
