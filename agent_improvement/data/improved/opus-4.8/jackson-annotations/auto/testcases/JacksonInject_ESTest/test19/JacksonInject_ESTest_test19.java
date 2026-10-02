package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test19 extends JacksonInject_ESTest_scaffolding {

    /**
     * Building a Value from a null annotation returns the shared EMPTY Value,
     * whose "optional" flag is unset (null).
     */
    @Test(timeout = 4000)
    public void fromNullAnnotation_hasNullOptional() throws Throwable {
        JacksonInject.Value valueFromNull = JacksonInject.Value.from((JacksonInject) null);

        assertNull(valueFromNull.getOptional());
    }
}
