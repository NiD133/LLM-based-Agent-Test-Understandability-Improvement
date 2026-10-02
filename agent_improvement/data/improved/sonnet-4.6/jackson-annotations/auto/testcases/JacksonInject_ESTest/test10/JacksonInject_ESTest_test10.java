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
public class JacksonInject_ESTest_test10 extends JacksonInject_ESTest_scaffolding {

    /**
     * When {@code withOptional(null)} is called on a Value whose optional flag is already null,
     * the method must return the same instance (no-op identity optimization) and preserve the id.
     */
    @Test(timeout = 4000)
    public void test_withOptional_null_returnsSameInstanceWhenOptionalAlreadyNull() throws Throwable {
        Object injectionId = new Object();
        JacksonInject.Value valueWithId = JacksonInject.Value.forId(injectionId);

        // optional is null after forId(); passing null again should return the same instance
        JacksonInject.Value valueAfterWithOptional = valueWithId.withOptional((Boolean) null);

        assertTrue(valueAfterWithOptional.hasId());
        assertSame(valueAfterWithOptional, valueWithId);
    }
}
