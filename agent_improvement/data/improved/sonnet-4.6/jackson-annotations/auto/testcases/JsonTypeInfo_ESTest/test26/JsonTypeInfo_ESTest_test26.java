package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test26 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test26() throws Throwable {
        // EMPTY already has Id.NONE; calling withIdType with the same Id should return
        // the exact same instance rather than creating a new one
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;
        JsonTypeInfo.Id noIdType = JsonTypeInfo.Id.NONE;
        JsonTypeInfo.Value valueWithSameIdType = emptyValue.withIdType(noIdType);
        assertSame(valueWithSameIdType, emptyValue);
    }
}
