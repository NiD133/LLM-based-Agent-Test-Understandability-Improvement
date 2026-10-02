package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test09 extends JacksonInject_ESTest_scaffolding {

    /**
     * The EMPTY Value has no injection id (it is constructed with a null id),
     * so hasId() must report false.
     */
    @Test(timeout = 4000)
    public void emptyValueHasNoId() throws Throwable {
        JacksonInject.Value emptyValue = JacksonInject.Value.EMPTY;

        boolean hasId = emptyValue.hasId();

        assertFalse(hasId);
    }
}
