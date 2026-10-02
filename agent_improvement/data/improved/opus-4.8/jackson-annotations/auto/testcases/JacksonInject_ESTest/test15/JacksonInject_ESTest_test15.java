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
     * Verifies that {@code withUseInput} returns the very same instance (no copy)
     * when the requested {@code useInput} value matches the current one, and that
     * the resulting Value still reports having an id.
     */
    @Test(timeout = 4000)
    public void withUseInput_sameValue_returnsSameInstanceAndKeepsId() throws Throwable {
        Object injectionId = new Object();
        Boolean useInput = Boolean.TRUE;

        // Build a Value whose useInput is already TRUE.
        JacksonInject.Value original =
                JacksonInject.Value.construct(injectionId, useInput, useInput);

        // Requesting the same useInput value must reuse the existing instance.
        JacksonInject.Value result = original.withUseInput(useInput);

        assertTrue("id was supplied, so hasId() must be true", result.hasId());
        assertSame("withUseInput with an unchanged value should not create a copy",
                result, original);
    }
}
