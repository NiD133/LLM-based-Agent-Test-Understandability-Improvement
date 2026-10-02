package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test12 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        // Build a Value using MINIMAL_CLASS id and WRAPPER_ARRAY inclusion, which together
        // constitute an "enabled" polymorphic type configuration
        JsonTypeInfo.Value typeInfoValue = JsonTypeInfo.Value.construct(
                JsonTypeInfo.Id.MINIMAL_CLASS,
                JsonTypeInfo.As.WRAPPER_ARRAY,
                "-HHcn77L=.C2",
                Object.class,
                false,
                Boolean.TRUE,
                Boolean.TRUE);

        boolean isEnabled = JsonTypeInfo.Value.isEnabled(typeInfoValue);

        assertEquals("-HHcn77L=.C2", typeInfoValue.getPropertyName());
        assertTrue(isEnabled);
        assertFalse(typeInfoValue.getIdVisible());
    }
}
