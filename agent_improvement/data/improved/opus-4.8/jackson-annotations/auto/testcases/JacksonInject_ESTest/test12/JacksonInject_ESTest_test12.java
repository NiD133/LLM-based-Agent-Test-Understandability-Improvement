package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test12 extends JacksonInject_ESTest_scaffolding {

    /**
     * Calling withOptional with the value the instance already holds is a no-op:
     * it returns the very same instance rather than creating a copy.
     */
    @Test(timeout = 4000)
    public void withOptional_givenSameValue_returnsSameInstance() throws Throwable {
        Object injectionId = new Object();
        Boolean optionalEnabled = Boolean.TRUE;

        JacksonInject.Value original =
                JacksonInject.Value.construct(injectionId, optionalEnabled, optionalEnabled);

        JacksonInject.Value result = original.withOptional(optionalEnabled);

        assertTrue("Value built with a non-null id should report having an id", result.hasId());
        assertSame("withOptional with an unchanged value should return the same instance",
                original, result);
    }
}
