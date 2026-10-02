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
     * Verifies that calling withOptional() with the same Boolean value already set
     * returns the exact same Value instance (no new object is created), and that
     * the resulting value still reports having an id.
     */
    @Test(timeout = 4000)
    public void test12() throws Throwable {
        Object injectionId = new Object();
        Boolean optional = Boolean.valueOf(true);

        // Construct a Value with a non-null id, useInput=true, and optional=true
        JacksonInject.Value value = JacksonInject.Value.construct(injectionId, optional, optional);

        // withOptional(true) should return the same instance because optional is already true
        JacksonInject.Value valueWithSameOptional = value.withOptional(optional);

        assertTrue(valueWithSameOptional.hasId());
        assertSame(valueWithSameOptional, value);
    }
}
