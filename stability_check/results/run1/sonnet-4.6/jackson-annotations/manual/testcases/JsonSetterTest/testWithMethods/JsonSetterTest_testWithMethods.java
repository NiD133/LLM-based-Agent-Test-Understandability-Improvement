package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonSetterTest_testWithMethods extends AnnotationTestUtil {

    private final JsonSetter.Value EMPTY = JsonSetter.Value.empty();

    @Test
    public void withContentNulls_nullArgument_returnsSameInstanceWhenAlreadyDefault() {
        JsonSetter.Value result = EMPTY.withContentNulls(null);
        assertSame(EMPTY, result);
    }

    @Test
    public void withContentNulls_newNonDefaultValue_updatesContentNulls() {
        JsonSetter.Value withFailContentNulls = EMPTY.withContentNulls(Nulls.FAIL);
        assertEquals(Nulls.FAIL, withFailContentNulls.getContentNulls());
    }

    @Test
    public void withContentNulls_sameValueAsExisting_returnsSameInstance() {
        JsonSetter.Value withFailContentNulls = EMPTY.withContentNulls(Nulls.FAIL);
        assertSame(withFailContentNulls, withFailContentNulls.withContentNulls(Nulls.FAIL));
    }

    @Test
    public void withValueNulls_newNonDefaultValue_updatesValueNulls() {
        JsonSetter.Value withFailContentNulls = EMPTY.withContentNulls(Nulls.FAIL);
        JsonSetter.Value withSkipValueNulls = withFailContentNulls.withValueNulls(Nulls.SKIP);
        assertEquals(Nulls.SKIP, withSkipValueNulls.getValueNulls());
    }

    @Test
    public void equals_differentNullsConfigurations_notEqual() {
        JsonSetter.Value withFailContentNulls = EMPTY.withContentNulls(Nulls.FAIL);
        JsonSetter.Value withSkipValueNulls = withFailContentNulls.withValueNulls(Nulls.SKIP);
        assertFalse(withFailContentNulls.equals(withSkipValueNulls));
        assertFalse(withSkipValueNulls.equals(withFailContentNulls));
    }

    @Test
    public void withValueNullsBothArgs_nullArguments_resetsValueAndContentNullsToDefault() {
        JsonSetter.Value withFailContentNulls = EMPTY.withContentNulls(Nulls.FAIL);
        JsonSetter.Value withSkipValueNulls = withFailContentNulls.withValueNulls(Nulls.SKIP);
        JsonSetter.Value allDefaults = withSkipValueNulls.withValueNulls(null, null);
        assertEquals(Nulls.DEFAULT, allDefaults.getContentNulls());
        assertEquals(Nulls.DEFAULT, allDefaults.getValueNulls());
    }

    @Test
    public void withValueNullsBothArgs_nullArgumentsWhenAlreadyDefault_returnsSameInstance() {
        JsonSetter.Value withFailContentNulls = EMPTY.withContentNulls(Nulls.FAIL);
        JsonSetter.Value withSkipValueNulls = withFailContentNulls.withValueNulls(Nulls.SKIP);
        JsonSetter.Value allDefaults = withSkipValueNulls.withValueNulls(null, null);
        assertSame(allDefaults, allDefaults.withValueNulls(null, null));
    }

    @Test
    public void withOverrides_nonDefaultOverride_overrideValuesWin_producesNewEqualInstance() {
        JsonSetter.Value withFailContentNulls = EMPTY.withContentNulls(Nulls.FAIL);
        JsonSetter.Value withSkipValueNulls = withFailContentNulls.withValueNulls(Nulls.SKIP);
        JsonSetter.Value allDefaults = withSkipValueNulls.withValueNulls(null, null);
        JsonSetter.Value merged = allDefaults.withOverrides(withSkipValueNulls);
        assertNotSame(withSkipValueNulls, merged);
        assertEquals(merged, withSkipValueNulls);
        assertEquals(withSkipValueNulls, merged);
    }
}
