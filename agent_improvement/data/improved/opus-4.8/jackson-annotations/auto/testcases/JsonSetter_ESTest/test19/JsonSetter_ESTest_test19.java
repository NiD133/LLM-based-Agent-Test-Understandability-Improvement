package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test19 extends JsonSetter_ESTest_scaffolding {

    /**
     * Verifies that merging a Value with a {@code null} override leaves the
     * original value's settings unchanged: {@link JsonSetter.Value#withOverrides}
     * returns {@code this} when handed a null override.
     */
    @Test(timeout = 4000)
    public void withOverrides_givenNullOverride_keepsOriginalNullsSettings() throws Throwable {
        JsonSetter.Value original = JsonSetter.Value.construct(Nulls.FAIL, Nulls.FAIL);

        JsonSetter.Value merged = original.withOverrides((JsonSetter.Value) null);

        assertEquals(Nulls.FAIL, merged.getValueNulls());
        assertEquals(Nulls.FAIL, merged.getContentNulls());
    }
}
