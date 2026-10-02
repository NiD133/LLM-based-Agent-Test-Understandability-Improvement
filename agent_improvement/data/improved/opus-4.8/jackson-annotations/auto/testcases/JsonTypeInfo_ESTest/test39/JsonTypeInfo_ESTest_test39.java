package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test39 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * The EMPTY Value is built without a "writeTypeIdForDefaultImpl" flag,
     * so its getter should report null (the unset state).
     */
    @Test(timeout = 4000)
    public void emptyValueHasNullWriteTypeIdForDefaultImpl() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        Boolean writeTypeIdForDefaultImpl = emptyValue.getWriteTypeIdForDefaultImpl();

        assertNull(writeTypeIdForDefaultImpl);
    }
}
