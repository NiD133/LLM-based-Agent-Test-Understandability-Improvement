package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test39 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * The "no overrides" Value leaves every accessor's visibility unset, so each
     * accessor reports Visibility.DEFAULT. Here we verify that for the creator visibility.
     */
    @Test(timeout = 4000)
    public void noOverridesValue_hasDefaultCreatorVisibility() throws Throwable {
        JsonAutoDetect.Value noOverrides = JsonAutoDetect.Value.noOverrides();

        JsonAutoDetect.Visibility creatorVisibility = noOverrides.getCreatorVisibility();

        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorVisibility);
    }
}
