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
public class JsonSetter_ESTest_test07 extends JsonSetter_ESTest_scaffolding {

    /**
     * When a JsonSetter.Value is built with Nulls.DEFAULT as the content-null
     * strategy, nonDefaultValueNulls() should return null because the value-null
     * strategy also defaults to Nulls.DEFAULT, and the method explicitly
     * suppresses the DEFAULT sentinel by returning null instead.
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        Nulls defaultNullStrategy = Nulls.DEFAULT;
        JsonSetter.Value valueWithDefaultContentNulls = JsonSetter.Value.forContentNulls(defaultNullStrategy);

        // nonDefaultValueNulls() returns null when _nulls == Nulls.DEFAULT
        Nulls nonDefaultValueNulls = valueWithDefaultContentNulls.nonDefaultValueNulls();
        assertNull(nonDefaultValueNulls);
    }
}
