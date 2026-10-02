package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JsonSetterTest_testWithMethods extends AnnotationTestUtil {

    private final JsonSetter.Value EMPTY = JsonSetter.Value.empty();

    @Test
    public void testWithMethods() {
        JsonSetter.Value unchangedEmpty = EMPTY.withContentNulls(null);
        assertSame(EMPTY, unchangedEmpty);

        JsonSetter.Value contentNullsFail = unchangedEmpty.withContentNulls(Nulls.FAIL);
        assertEquals(Nulls.FAIL, contentNullsFail.getContentNulls());
        assertSame(contentNullsFail, contentNullsFail.withContentNulls(Nulls.FAIL));

        JsonSetter.Value valueNullsSkip = contentNullsFail.withValueNulls(Nulls.SKIP);
        assertEquals(Nulls.SKIP, valueNullsSkip.getValueNulls());
        assertFalse(contentNullsFail.equals(valueNullsSkip));
        assertFalse(valueNullsSkip.equals(contentNullsFail));

        JsonSetter.Value resetToDefaults = valueNullsSkip.withValueNulls(null, null);
        assertEquals(Nulls.DEFAULT, resetToDefaults.getContentNulls());
        assertEquals(Nulls.DEFAULT, resetToDefaults.getValueNulls());
        assertSame(resetToDefaults, resetToDefaults.withValueNulls(null, null));

        JsonSetter.Value mergedWithOverrides = resetToDefaults.withOverrides(valueNullsSkip);
        assertNotSame(valueNullsSkip, mergedWithOverrides);
        assertEquals(mergedWithOverrides, valueNullsSkip);
        assertEquals(valueNullsSkip, mergedWithOverrides);
    }
}
