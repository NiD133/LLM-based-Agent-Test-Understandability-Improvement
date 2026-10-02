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
public class JsonTypeInfo_ESTest_test16 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        JsonTypeInfo.Id idType = JsonTypeInfo.Id.CUSTOM;
        JsonTypeInfo.As inclusionType = JsonTypeInfo.As.NOTHING;
        Class<Object> defaultImplementation = Object.class;
        Boolean disabledFlag = Boolean.FALSE;

        JsonTypeInfo.Value typeInfo = JsonTypeInfo.Value.construct(
                idType,
                inclusionType,
                "G",
                defaultImplementation,
                false,
                disabledFlag,
                disabledFlag);

        boolean shouldWriteDefaultImplementationTypeId = typeInfo.shouldWriteTypeIdForDefaultImpl();

        assertFalse(shouldWriteDefaultImplementationTypeId);
        assertFalse(typeInfo.getIdVisible());
    }
}
