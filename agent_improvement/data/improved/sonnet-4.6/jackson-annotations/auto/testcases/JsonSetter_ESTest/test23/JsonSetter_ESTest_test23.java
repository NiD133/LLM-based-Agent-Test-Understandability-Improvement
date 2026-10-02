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
public class JsonSetter_ESTest_test23 extends JsonSetter_ESTest_scaffolding {

    /**
     * Verifies that deserializing an empty JsonSetter.Value (one with all-DEFAULT
     * null-handling settings) resolves to the shared EMPTY singleton, which reports
     * Nulls.DEFAULT for its content-nulls handling strategy.
     */
    @Test(timeout = 4000)
    public void test23() throws Throwable {
        JsonSetter.Value emptyValue = JsonSetter.Value.empty();
        JsonSetter.Value resolvedValue = (JsonSetter.Value) emptyValue.readResolve();
        assertEquals(Nulls.DEFAULT, resolvedValue.getContentNulls());
    }
}
