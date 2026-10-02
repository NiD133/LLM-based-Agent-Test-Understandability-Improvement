package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test31 extends JsonIgnoreProperties_ESTest_scaffolding {

    // Verifies that merging a base Value with null overrides returns the same base instance unchanged.
    @Test(timeout = 4000)
    public void test_mergeWithNullOverrides_returnsSameBaseInstance() throws Throwable {
        JsonIgnoreProperties.Value base = JsonIgnoreProperties.Value.empty();
        JsonIgnoreProperties.Value result = JsonIgnoreProperties.Value.merge(base, (JsonIgnoreProperties.Value) null);
        assertSame(base, result);
    }
}
