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
public class JsonSetter_ESTest_test08 extends JsonSetter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        Nulls configuredNullHandling = Nulls.AS_EMPTY;
        JsonSetter.Value setterValue = JsonSetter.Value.forValueNulls(
                configuredNullHandling, configuredNullHandling);

        setterValue.nonDefaultValueNulls();

        assertEquals(Nulls.AS_EMPTY, setterValue.getValueNulls());
        assertEquals(Nulls.AS_EMPTY, setterValue.getContentNulls());
    }
}
