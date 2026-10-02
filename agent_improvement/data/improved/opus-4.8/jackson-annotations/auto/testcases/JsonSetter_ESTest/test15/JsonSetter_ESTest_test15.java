package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test15 extends JsonSetter_ESTest_scaffolding {

    /**
     * Applying overrides that explicitly set both nulls-handling values onto the
     * EMPTY value (which uses DEFAULT for both) should yield a value carrying the
     * overrides for both valueNulls and contentNulls.
     */
    @Test(timeout = 4000)
    public void withOverrides_appliesBothNullsFromOverrideOntoEmptyBase() throws Throwable {
        JsonSetter.Value overrides = JsonSetter.Value.construct(Nulls.FAIL, Nulls.FAIL);
        JsonSetter.Value emptyBase = JsonSetter.Value.EMPTY;

        JsonSetter.Value merged = emptyBase.withOverrides(overrides);

        assertEquals(Nulls.FAIL, merged.getValueNulls());
        assertEquals(Nulls.FAIL, merged.getContentNulls());
    }
}
