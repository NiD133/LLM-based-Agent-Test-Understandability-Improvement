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
public class JsonTypeInfo_ESTest_test04 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that withInclusionType() produces a distinct Value instance
     * that is not equal to the original, while preserving the idVisible flag.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;
        JsonTypeInfo.As wrapperArrayInclusion = JsonTypeInfo.As.WRAPPER_ARRAY;

        // Changing the inclusion type should produce a new, distinct Value
        JsonTypeInfo.Value valueWithWrapperArray = emptyValue.withInclusionType(wrapperArrayInclusion);

        boolean newValueEqualsOriginal = valueWithWrapperArray.equals(emptyValue);

        // The new value has a different inclusion type, so idVisible remains false (unchanged)
        assertFalse(valueWithWrapperArray.getIdVisible());
        // The two values differ (emptyValue has As.NOTHING; valueWithWrapperArray has As.WRAPPER_ARRAY)
        assertFalse(emptyValue.equals((Object) valueWithWrapperArray));
        assertFalse(newValueEqualsOriginal);
    }
}
