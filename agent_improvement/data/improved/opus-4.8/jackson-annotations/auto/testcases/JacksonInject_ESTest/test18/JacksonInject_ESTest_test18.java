package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test18 extends JacksonInject_ESTest_scaffolding {

    /**
     * Clearing the id with {@code withId(null)} should yield a new, distinct
     * Value instance that no longer reports having an id.
     */
    @Test(timeout = 4000)
    public void withIdNull_returnsNewValueWithoutId() throws Throwable {
        Object injectionId = new Object();
        JacksonInject.Value valueWithId = JacksonInject.Value.forId(injectionId);

        JacksonInject.Value valueWithoutId = valueWithId.withId(null);

        assertNotSame(valueWithoutId, valueWithId);
        assertFalse(valueWithoutId.hasId());
    }
}
