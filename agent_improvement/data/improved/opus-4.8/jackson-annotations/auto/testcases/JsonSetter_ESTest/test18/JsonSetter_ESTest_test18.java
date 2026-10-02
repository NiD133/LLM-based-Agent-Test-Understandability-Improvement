package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test18 extends JsonSetter_ESTest_scaffolding {

    /**
     * Applying EMPTY overrides should leave the base value's null-handling
     * settings unchanged, because EMPTY carries no explicit values to override with.
     */
    @Test(timeout = 4000)
    public void withEmptyOverridesKeepsOriginalNullsSettings() throws Throwable {
        JsonSetter.Value baseValue = JsonSetter.Value.construct(Nulls.FAIL, Nulls.FAIL);
        JsonSetter.Value emptyOverrides = JsonSetter.Value.EMPTY;

        JsonSetter.Value merged = baseValue.withOverrides(emptyOverrides);

        assertEquals(Nulls.FAIL, merged.getContentNulls());
        assertEquals(Nulls.FAIL, merged.getValueNulls());
    }
}
