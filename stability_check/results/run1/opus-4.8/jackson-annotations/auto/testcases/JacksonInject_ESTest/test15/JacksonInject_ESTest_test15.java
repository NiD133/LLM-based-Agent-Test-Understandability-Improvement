package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test15 extends JacksonInject_ESTest_scaffolding {

    /**
     * Calling withUseInput with the value already held by the Value returns the
     * same instance (no copy), and the id set at construction is preserved.
     */
    @Test(timeout = 4000)
    public void withUseInputUsingSameValueReturnsSameInstance() throws Throwable {
        Object injectId = new Object();
        Boolean useInput = Boolean.TRUE;

        JacksonInject.Value original =
                JacksonInject.Value.construct(injectId, useInput, useInput);
        JacksonInject.Value result = original.withUseInput(useInput);

        assertTrue("Value should report having an id", result.hasId());
        assertSame("Re-applying the same useInput should not create a new Value",
                original, result);
    }
}
