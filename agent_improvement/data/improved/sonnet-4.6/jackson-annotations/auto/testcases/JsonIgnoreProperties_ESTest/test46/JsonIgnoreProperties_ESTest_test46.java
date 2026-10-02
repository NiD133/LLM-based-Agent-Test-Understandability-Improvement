package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test46 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_emptyValue_getAllowSetters_returnsFalse() throws Throwable {
        // The empty Value has allowSetters disabled by default
        JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.empty();
        boolean allowSetters = emptyValue.getAllowSetters();
        assertFalse(allowSetters);
    }
}
