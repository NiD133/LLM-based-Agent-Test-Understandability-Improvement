package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test09 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;
        // A Value instance must be equal to itself (reflexive property of equals)
        boolean isEqualToItself = emptyValue.equals(emptyValue);
        assertTrue(isEqualToItself);
    }
}
