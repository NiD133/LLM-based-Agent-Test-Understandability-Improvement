package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test17 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        JsonTypeInfo.Id typeIdStrategy = JsonTypeInfo.Id.SIMPLE_NAME;
        JsonTypeInfo.As typeInclusionStrategy = JsonTypeInfo.As.WRAPPER_OBJECT;
        Class<Object> defaultImplementation = Object.class;
        Boolean enabledFlag = new Boolean(true);

        JsonTypeInfo.Value typeInfo = JsonTypeInfo.Value.construct(
                typeIdStrategy,
                typeInclusionStrategy,
                "WRAPPER_ARRAY",
                defaultImplementation,
                true,
                enabledFlag,
                enabledFlag);

        boolean shouldWriteTypeIdForDefaultImpl = typeInfo.shouldWriteTypeIdForDefaultImpl();

        assertTrue(shouldWriteTypeIdForDefaultImpl);
        assertEquals("WRAPPER_ARRAY", typeInfo.getPropertyName());
        assertTrue(typeInfo.getIdVisible());
    }
}
