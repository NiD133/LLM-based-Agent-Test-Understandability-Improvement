package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test27 extends JacksonInject_ESTest_scaffolding {

    /**
     * The shared empty Value should carry no "optional" override,
     * so getOptional() returns null.
     */
    @Test(timeout = 4000)
    public void emptyValueHasNoOptionalSetting() throws Throwable {
        JacksonInject.Value emptyValue = JacksonInject.Value.empty();

        assertNull(emptyValue.getOptional());
    }
}
