package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.LinkedHashSet;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test21 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test21() throws Throwable {
        JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.empty();
        JsonIgnoreProperties.Value allowSettersValue = emptyValue.withAllowSetters();
        JsonIgnoreProperties.Value repeatedAllowSettersValue = allowSettersValue.withAllowSetters();

        assertFalse(repeatedAllowSettersValue.getIgnoreUnknown());
        assertTrue(repeatedAllowSettersValue.getAllowSetters());
        assertFalse(repeatedAllowSettersValue.getAllowGetters());
        assertTrue(repeatedAllowSettersValue.getMerge());
        assertSame(repeatedAllowSettersValue, allowSettersValue);
    }
}
