package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test16 extends JsonSetter_ESTest_scaffolding {

    /**
     * Merging a value over a base that already carries the override's value-nulls
     * (and a more specific content-nulls) returns the base unchanged, while the
     * override itself keeps its own distinct settings.
     */
    @Test(timeout = 4000)
    public void mergeReturnsBaseWhenOverrideAddsNothingNew() throws Throwable {
        // Base has FAIL for both value-nulls and content-nulls.
        JsonSetter.Value base = JsonSetter.Value.construct(Nulls.FAIL, Nulls.FAIL);
        // Override only sets value-nulls to FAIL; content-nulls stays at DEFAULT.
        JsonSetter.Value override = JsonSetter.Value.forValueNulls(Nulls.FAIL);

        JsonSetter.Value merged = JsonSetter.Value.merge(base, override);

        // The two values differ (DEFAULT vs FAIL content-nulls), so they are not equal.
        assertFalse(override.equals((Object) base));
        // Override contributes no new setting, so merge returns the base instance itself.
        assertSame(base, merged);
        // The override retained its requested value-nulls.
        assertEquals(Nulls.FAIL, override.getValueNulls());
    }
}
