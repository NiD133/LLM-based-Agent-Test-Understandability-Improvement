package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

/**
 * Tests that JacksonInject.Value correctly reports a non-null injection ID,
 * and that withUseInput() returns the same instance when the useInput flag
 * is unchanged (identity/no-op optimization).
 */
@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test15 extends JacksonInject_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_withUseInput_returnsSameInstanceWhenValueUnchanged() throws Throwable {
        // Construct a Value with a non-null ID and useInput=true
        Object injectionId = new Object();
        Boolean useInput = Boolean.TRUE;
        JacksonInject.Value value = JacksonInject.Value.construct(injectionId, useInput, useInput);

        // Calling withUseInput with the same value should return the identical instance
        JacksonInject.Value valueAfterNoOpUpdate = value.withUseInput(useInput);

        // The value still has the original non-null ID
        assertTrue(valueAfterNoOpUpdate.hasId());
        // No new object was created — the same instance is returned
        assertSame(valueAfterNoOpUpdate, value);
    }
}
