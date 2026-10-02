package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test04 extends JacksonInject_ESTest_scaffolding {

    /**
     * Verifies that a JacksonInject.Value built with a non-null id:
     *  - reports that it has an id via hasId(), and
     *  - is not considered equal to an unrelated object (a Boolean).
     */
    @Test(timeout = 4000)
    public void valueWithId_hasId_andIsNotEqualToUnrelatedObject() throws Throwable {
        Object injectId = new Object();
        Boolean useInputAndOptional = Boolean.TRUE;

        JacksonInject.Value injectValue =
                JacksonInject.Value.construct(injectId, useInputAndOptional, useInputAndOptional);

        // A JacksonInject.Value is never equal to an object of a different type.
        boolean equalsUnrelatedObject = injectValue.equals(useInputAndOptional);
        assertFalse(equalsUnrelatedObject);

        // The id was non-null, so hasId() must be true.
        assertTrue(injectValue.hasId());
    }
}
