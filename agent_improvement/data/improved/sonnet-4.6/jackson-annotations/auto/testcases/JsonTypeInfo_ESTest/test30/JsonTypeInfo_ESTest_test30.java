package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test30 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test30() throws Throwable {
        // Build a Value with idVisible=true and verify getIdVisible() returns true
        JsonTypeInfo.Value value = JsonTypeInfo.Value.construct(
                JsonTypeInfo.Id.CUSTOM,
                JsonTypeInfo.As.EXTERNAL_PROPERTY,
                "Gr9fYPjBd{JN",
                (Class<?>) null,
                true,
                Boolean.TRUE,
                Boolean.TRUE);

        assertTrue(value.getIdVisible());
    }
}
