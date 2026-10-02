package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test32 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * The shared EMPTY Value is constructed with a {@code null} default
     * implementation, so {@link JsonTypeInfo.Value#getDefaultImpl()} should
     * report that no default implementation is configured.
     */
    @Test(timeout = 4000)
    public void emptyValueHasNoDefaultImpl() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        Class<?> defaultImpl = emptyValue.getDefaultImpl();

        assertNull(defaultImpl);
    }
}
