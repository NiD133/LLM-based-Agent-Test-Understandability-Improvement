package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test33 extends JsonAutoDetect_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_defaultValue_toStringReflectsBaselineVisibilitySettings() throws Throwable {
        // DEFAULT uses PUBLIC_ONLY for fields/getters/isGetters/creators, ANY for setters, NON_PRIVATE for scalarConstructors
        JsonAutoDetect.Value defaultVisibility = JsonAutoDetect.Value.DEFAULT;

        String representation = defaultVisibility.toString();

        assertEquals(
            "JsonAutoDetect.Value(fields=PUBLIC_ONLY,getters=PUBLIC_ONLY,isGetters=PUBLIC_ONLY,setters=ANY,creators=PUBLIC_ONLY,scalarConstructors=NON_PRIVATE)",
            representation
        );
    }
}
