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
public class JsonSetter_ESTest_test12 extends JsonSetter_ESTest_scaffolding {

    private static final Nulls UNSPECIFIED_VALUE_NULLS = null;
    private static final Nulls UNSPECIFIED_CONTENT_NULLS = null;

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        JsonSetter.Value emptySetterValue = JsonSetter.Value.empty();

        JsonSetter.Value valueWithDefaultNullHandling = emptySetterValue.withValueNulls(
                UNSPECIFIED_VALUE_NULLS,
                UNSPECIFIED_CONTENT_NULLS);

        assertEquals(Nulls.DEFAULT, valueWithDefaultNullHandling.getValueNulls());
    }
}
