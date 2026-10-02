package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test16 extends JacksonInject_ESTest_scaffolding {

    /**
     * Verifies that calling {@link JacksonInject.Value#withId(Object)} with the
     * same id the Value already holds is a no-op that returns the very same
     * instance (rather than allocating an equivalent copy).
     */
    @Test(timeout = 4000)
    public void withId_sameId_returnsSameInstance() throws Throwable {
        Object injectionId = new Object();
        JacksonInject.Value original = JacksonInject.Value.forId(injectionId);

        JacksonInject.Value result = original.withId(injectionId);

        assertSame(original, result);
    }
}
