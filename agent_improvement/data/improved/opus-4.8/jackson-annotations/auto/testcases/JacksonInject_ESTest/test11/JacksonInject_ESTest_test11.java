package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test11 extends JacksonInject_ESTest_scaffolding {

    /**
     * Verifies that {@link JacksonInject.Value#withOptional(Boolean)} produces a new,
     * distinct Value instance when the optional flag changes (here from TRUE to null),
     * while leaving the original instance unchanged.
     */
    @Test(timeout = 4000)
    public void withOptionalChangingFlagReturnsDistinctValue() throws Throwable {
        Object injectionId = new Object();
        Boolean useInput = Boolean.TRUE;
        Boolean optional = Boolean.TRUE;

        JacksonInject.Value original =
                JacksonInject.Value.construct(injectionId, useInput, optional);
        JacksonInject.Value withOptionalCleared = original.withOptional((Boolean) null);

        // The id is carried over, so the new Value still reports having an id.
        assertTrue(withOptionalCleared.hasId());
        // Clearing the optional flag yields a value that differs from the original...
        assertFalse(withOptionalCleared.equals(original));
        // ...and is a brand new instance, not the same object.
        assertNotSame(withOptionalCleared, original);
    }
}
