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
public class JsonTypeInfo_ESTest_test30 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test30() throws Throwable {
        JsonTypeInfo.Id customTypeId = JsonTypeInfo.Id.CUSTOM;
        JsonTypeInfo.As externalPropertyInclusion = JsonTypeInfo.As.EXTERNAL_PROPERTY;
        String typePropertyName = "Gr9fYPjBd{JN";
        Class<?> defaultImplementation = null;
        boolean idShouldBeVisible = true;
        Boolean requireTypeIdForSubtypes = Boolean.TRUE;
        Boolean requireTypeIdForProperties = Boolean.TRUE;

        JsonTypeInfo.Value typeInfoValue = JsonTypeInfo.Value.construct(
                customTypeId,
                externalPropertyInclusion,
                typePropertyName,
                defaultImplementation,
                idShouldBeVisible,
                requireTypeIdForSubtypes,
                requireTypeIdForProperties);

        assertTrue(typeInfoValue.getIdVisible());
    }
}
