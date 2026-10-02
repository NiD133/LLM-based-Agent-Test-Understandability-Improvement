package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test06 extends JacksonInject_ESTest_scaffolding {

    /**
     * When a Value is constructed with a non-null id but a null "useInput" flag,
     * willUseInput should fall back to the supplied default setting, and hasId
     * should report that an id is present.
     */
    @Test(timeout = 4000)
    public void willUseInputFallsBackToDefaultAndHasIdIsTrue() throws Throwable {
        Object injectionId = new Object();
        Boolean useInput = null;
        Boolean optional = Boolean.TRUE;

        JacksonInject.Value value = JacksonInject.Value.construct(injectionId, useInput, optional);

        // useInput is null, so willUseInput returns the default we pass in (false).
        boolean willUseInput = value.willUseInput(false);
        assertFalse(willUseInput);

        // A non-null id was supplied, so hasId is true.
        assertTrue(value.hasId());
    }
}
