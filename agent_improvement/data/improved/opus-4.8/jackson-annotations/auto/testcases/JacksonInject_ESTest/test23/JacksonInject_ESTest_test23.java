package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test23 extends JacksonInject_ESTest_scaffolding {

    /**
     * The EMPTY value is constructed with a null "optional" flag, so
     * getOptional() should report null (meaning "no explicit setting").
     */
    @Test(timeout = 4000)
    public void getOptionalOnEmptyValueReturnsNull() throws Throwable {
        JacksonInject.Value emptyValue = JacksonInject.Value.EMPTY;

        Boolean optional = emptyValue.getOptional();

        assertNull(optional);
    }
}
