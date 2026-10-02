package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test63 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_arrayShapeIsStructured() throws Throwable {
        // ARRAY is a structured (container) shape, so isStructured should return true
        boolean isStructured = JsonFormat.Shape.isStructured(JsonFormat.Shape.ARRAY);
        assertTrue(isStructured);
    }
}
