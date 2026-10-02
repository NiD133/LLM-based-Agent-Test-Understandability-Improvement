package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JacksonInject_ESTest_test14 extends JacksonInject_ESTest_scaffolding {

    /**
     * The EMPTY value already has a null useInput, so requesting the same
     * (null) useInput should be a no-op that returns the very same instance
     * rather than allocating a new Value.
     */
    @Test(timeout = 4000)
    public void withUseInputNullOnEmptyReturnsSameInstance() throws Throwable {
        JacksonInject.Value emptyValue = JacksonInject.Value.EMPTY;

        JacksonInject.Value result = emptyValue.withUseInput((Boolean) null);

        assertSame(emptyValue, result);
    }
}
