package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test17 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        JsonTypeInfo.Value value = JsonTypeInfo.Value.construct(
                JsonTypeInfo.Id.SIMPLE_NAME,
                JsonTypeInfo.As.WRAPPER_OBJECT,
                "WRAPPER_ARRAY",
                Object.class,
                true,
                Boolean.TRUE,
                Boolean.TRUE);

        assertTrue(value.shouldWriteTypeIdForDefaultImpl());
        assertEquals("WRAPPER_ARRAY", value.getPropertyName());
        assertTrue(value.getIdVisible());
    }
}
