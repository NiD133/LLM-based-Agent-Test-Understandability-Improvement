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
        JsonTypeInfo.Id disabledTypeId = JsonTypeInfo.Id.NONE;
        JsonTypeInfo.As wrapperObjectInclusion = JsonTypeInfo.As.WRAPPER_OBJECT;
        Class<Integer> defaultImplementation = Integer.class;

        Boolean originalBooleanFlag = Boolean.valueOf("-0VzDY5^*");
        JsonTypeInfo.Value originalValue = new JsonTypeInfo.Value(
                disabledTypeId,
                wrapperObjectInclusion,
                "-0VzDY5^*",
                defaultImplementation,
                false,
                originalBooleanFlag,
                originalBooleanFlag);

        Boolean replacementBooleanFlag = new Boolean(false);
        JsonTypeInfo.Value valueWithReplacementFlag =
                originalValue.withWriteTypeIdForDefaultImpl(replacementBooleanFlag);

        boolean valuesAreEqual = originalValue.equals(valueWithReplacementFlag);

        assertTrue(valuesAreEqual);
        assertNotSame(valueWithReplacementFlag, originalValue);
        assertFalse(valueWithReplacementFlag.shouldWriteTypeIdForDefaultImpl());
    }
}
