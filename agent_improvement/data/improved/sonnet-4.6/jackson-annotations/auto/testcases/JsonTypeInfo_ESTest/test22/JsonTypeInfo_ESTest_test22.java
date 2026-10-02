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
public class JsonTypeInfo_ESTest_test22 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that withIdVisible() returns the same Value instance when the
     * requested visibility matches the current setting (no-op identity optimization).
     *
     * EMPTY has idVisible=false by default, so calling withIdVisible(false) should
     * return the exact same object rather than allocating a new one.
     */
    @Test(timeout = 4000)
    public void test22() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        // EMPTY has idVisible=false; requesting false again is a no-op
        JsonTypeInfo.Value resultValue = emptyValue.withIdVisible(false);

        // The implementation must return the same instance to preserve identity
        assertSame(resultValue, emptyValue);
    }
}
