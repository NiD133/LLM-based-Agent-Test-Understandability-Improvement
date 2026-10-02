package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test25 extends JsonSetter_ESTest_scaffolding {

    /**
     * When merging with a null base, the overrides instance should be returned
     * as-is, preserving both its value-nulls and content-nulls settings.
     */
    @Test(timeout = 4000)
    public void mergeWithNullBaseKeepsOverrideNullsSettings() throws Throwable {
        JsonSetter.Value overrides = JsonSetter.Value.forValueNulls(Nulls.FAIL, Nulls.FAIL);

        JsonSetter.Value merged = JsonSetter.Value.merge((JsonSetter.Value) null, overrides);

        assertNotNull(merged);
        assertEquals(Nulls.FAIL, merged.getValueNulls());
        assertEquals(Nulls.FAIL, merged.getContentNulls());
    }
}
