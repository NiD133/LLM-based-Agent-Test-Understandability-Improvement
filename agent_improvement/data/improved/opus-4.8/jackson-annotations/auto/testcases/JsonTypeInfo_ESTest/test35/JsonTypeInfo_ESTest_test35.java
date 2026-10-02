package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test35 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * The EMPTY Value is constructed with Id.NONE, so its id type should be NONE.
     */
    @Test(timeout = 4000)
    public void emptyValueHasIdTypeNone() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        JsonTypeInfo.Id idType = emptyValue.getIdType();

        assertEquals(JsonTypeInfo.Id.NONE, idType);
    }
}
