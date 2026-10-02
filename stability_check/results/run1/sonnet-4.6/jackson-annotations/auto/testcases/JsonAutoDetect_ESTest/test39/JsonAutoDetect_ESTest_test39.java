package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test39 extends JsonAutoDetect_ESTest_scaffolding {

    // noOverrides() produces a Value where every visibility is DEFAULT (no overrides applied).
    @Test(timeout = 4000)
    public void test_noOverrides_creatorVisibilityIsDefault() throws Throwable {
        JsonAutoDetect.Value noOverridesValue = JsonAutoDetect.Value.noOverrides();
        JsonAutoDetect.Visibility creatorVisibility = noOverridesValue.getCreatorVisibility();
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorVisibility);
    }
}
