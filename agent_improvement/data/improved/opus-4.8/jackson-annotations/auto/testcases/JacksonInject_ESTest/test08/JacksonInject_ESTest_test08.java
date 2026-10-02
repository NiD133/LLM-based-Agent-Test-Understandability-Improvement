package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test08 extends JacksonInject_ESTest_scaffolding {

    /**
     * Verifies that deriving a Value from the empty one with a non-null id
     * results in a Value that reports having an id.
     */
    @Test(timeout = 4000)
    public void withId_givenNonNullId_hasIdReturnsTrue() throws Throwable {
        JacksonInject.Value emptyValue = JacksonInject.Value.EMPTY;

        // Use the empty Value itself as the (non-null) injected id.
        JacksonInject.Value valueWithId = emptyValue.withId(emptyValue);

        assertTrue(valueWithId.hasId());
    }
}
