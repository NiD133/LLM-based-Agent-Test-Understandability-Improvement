package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test19 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * The EMPTY value already has a null "writeTypeIdForDefaultImpl" flag, so
     * asking for that same null value is a no-op: withWriteTypeIdForDefaultImpl
     * should return the very same instance rather than allocating a new one.
     */
    @Test(timeout = 4000)
    public void withSameWriteTypeIdForDefaultImpl_returnsSameInstance() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        JsonTypeInfo.Value result = emptyValue.withWriteTypeIdForDefaultImpl((Boolean) null);

        assertSame(emptyValue, result);
    }
}
