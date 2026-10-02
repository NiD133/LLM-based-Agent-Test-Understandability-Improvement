package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test18 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * The EMPTY value leaves {@code writeTypeIdForDefaultImpl} unset (null),
     * so {@code shouldWriteTypeIdForDefaultImpl()} should fall back to its
     * backwards-compatible default of {@code true}.
     */
    @Test(timeout = 4000)
    public void shouldWriteTypeIdForDefaultImpl_returnsTrue_forEmptyValue() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        boolean shouldWrite = emptyValue.shouldWriteTypeIdForDefaultImpl();

        assertTrue(shouldWrite);
    }
}
