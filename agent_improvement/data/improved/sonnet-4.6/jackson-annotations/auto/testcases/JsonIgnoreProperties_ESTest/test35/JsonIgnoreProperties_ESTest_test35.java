package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test35 extends JsonIgnoreProperties_ESTest_scaffolding {

    // When both arguments to merge() are null, the result should also be null,
    // because merge returns the overrides argument when base is null.
    @Test(timeout = 4000)
    public void test_mergeReturnsNullWhenBothBaseAndOverridesAreNull() throws Throwable {
        JsonIgnoreProperties.Value result = JsonIgnoreProperties.Value.merge(
                (JsonIgnoreProperties.Value) null,
                (JsonIgnoreProperties.Value) null);
        assertNull(result);
    }
}
