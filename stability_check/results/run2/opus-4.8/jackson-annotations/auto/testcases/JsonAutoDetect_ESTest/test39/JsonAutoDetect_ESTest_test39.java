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
     * The "no overrides" Value leaves every accessor at Visibility.DEFAULT,
     * so its creator visibility should also report DEFAULT.
     */
    @Test(timeout = 4000)
    public void noOverridesValueReturnsDefaultCreatorVisibility() throws Throwable {
        JsonAutoDetect.Value noOverrides = JsonAutoDetect.Value.noOverrides();

        JsonAutoDetect.Visibility creatorVisibility = noOverrides.getCreatorVisibility();

        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorVisibility);
    }
}
