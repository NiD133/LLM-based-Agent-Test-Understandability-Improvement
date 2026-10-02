package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test10 extends JacksonInject_ESTest_scaffolding {

    /**
     * Verifies that calling {@code withOptional(null)} on a Value whose optional
     * flag is already {@code null} is a no-op: it returns the very same instance
     * rather than creating a new one, and the injection id is preserved.
     */
    @Test(timeout = 4000)
    public void withOptionalNull_whenOptionalAlreadyNull_returnsSameInstance() throws Throwable {
        Object injectionId = new Object();
        JacksonInject.Value valueWithId = JacksonInject.Value.forId(injectionId);

        JacksonInject.Value result = valueWithId.withOptional((Boolean) null);

        assertTrue("id supplied via forId(...) should be reported by hasId()", result.hasId());
        assertSame("withOptional(null) should reuse the existing instance when optional is unchanged",
                valueWithId, result);
    }
}
