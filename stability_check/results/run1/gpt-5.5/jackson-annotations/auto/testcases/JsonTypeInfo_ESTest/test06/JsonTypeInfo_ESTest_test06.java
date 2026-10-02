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
public class JsonTypeInfo_ESTest_test06 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        JsonTypeInfo.Id idType = JsonTypeInfo.Id.NONE;
        JsonTypeInfo.As inclusionType = JsonTypeInfo.As.WRAPPER_OBJECT;
        Class<Integer> defaultImplementation = Integer.class;
        Boolean requiredTypeIdForSubtypes = Boolean.valueOf("-0VzDY5^*");

        JsonTypeInfo.Value valueWithDefaultWriteSetting = new JsonTypeInfo.Value(
                idType,
                inclusionType,
                "-0VzDY5^*",
                defaultImplementation,
                false,
                requiredTypeIdForSubtypes,
                requiredTypeIdForSubtypes);

        Boolean doNotWriteTypeIdForDefaultImplementation = new Boolean(false);
        JsonTypeInfo.Value valueWithoutDefaultImplTypeId = valueWithDefaultWriteSetting
                .withWriteTypeIdForDefaultImpl(doNotWriteTypeIdForDefaultImplementation);

        boolean valuesRemainEqual = valueWithDefaultWriteSetting.equals(valueWithoutDefaultImplTypeId);
        assertTrue(valuesRemainEqual);
        assertNotSame(valueWithoutDefaultImplTypeId, valueWithDefaultWriteSetting);
        assertFalse(valueWithoutDefaultImplTypeId.shouldWriteTypeIdForDefaultImpl());
    }
}
