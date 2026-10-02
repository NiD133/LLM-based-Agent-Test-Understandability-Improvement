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
public class JacksonInject_ESTest_test15 extends JacksonInject_ESTest_scaffolding {

    /**
     * Verifies that calling withUseInput() with the same Boolean value that is already set
     * returns the exact same Value instance (identity optimization), and that the injection
     * id is still present on the returned instance.
     */
    @Test(timeout = 4000)
    public void test15() throws Throwable {
        Object injectionId = new Object();
        Boolean useInput = Boolean.valueOf(true);

        // Construct a Value with a non-null id and useInput=true, optional=true
        JacksonInject.Value originalValue = JacksonInject.Value.construct(injectionId, useInput, useInput);

        // withUseInput() should return the same instance when the value is unchanged
        JacksonInject.Value unchangedValue = originalValue.withUseInput(useInput);

        assertTrue(unchangedValue.hasId());
        assertSame(unchangedValue, originalValue);
    }
}
