package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test13 extends JacksonInject_ESTest_scaffolding {

    /**
     * Verifies that {@link JacksonInject.Value#withUseInput(Boolean)} produces a new,
     * distinct Value when the useInput flag changes (here from TRUE to null), while
     * the original id is preserved.
     */
    @Test(timeout = 4000)
    public void withUseInput_changingFlag_returnsDistinctValueKeepingId() throws Throwable {
        Object injectionId = new Object();
        JacksonInject.Value original =
                JacksonInject.Value.construct(injectionId, Boolean.TRUE, Boolean.TRUE);

        JacksonInject.Value modified = original.withUseInput((Boolean) null);

        // Changing useInput yields a brand new, non-equal Value instance.
        assertNotSame(modified, original);
        assertFalse(modified.equals(original));
        // The injection id carries over, so the modified Value still has an id.
        assertTrue(modified.hasId());
    }
}
