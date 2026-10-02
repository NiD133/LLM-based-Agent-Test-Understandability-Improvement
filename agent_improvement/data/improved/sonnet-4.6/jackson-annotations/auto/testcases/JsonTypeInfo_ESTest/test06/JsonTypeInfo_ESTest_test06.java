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

    /**
     * Verifies that withWriteTypeIdForDefaultImpl() creates a new Value instance
     * when called with a distinct Boolean object (reference inequality), while
     * the resulting Value remains structurally equal to the original.
     * Also verifies that shouldWriteTypeIdForDefaultImpl() returns false when
     * _writeTypeIdForDefaultImpl is set to Boolean.FALSE.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // Set up the configuration parameters for a base JsonTypeInfo.Value
        JsonTypeInfo.Id idType = JsonTypeInfo.Id.NONE;
        JsonTypeInfo.As inclusionType = JsonTypeInfo.As.WRAPPER_OBJECT;
        Class<Integer> defaultImpl = Integer.class;
        // Boolean.valueOf of a non-"true" string evaluates to Boolean.FALSE
        Boolean writeTypeIdFalse = Boolean.valueOf("-0VzDY5^*");

        JsonTypeInfo.Value baseValue = new JsonTypeInfo.Value(
                idType, inclusionType, "-0VzDY5^*", defaultImpl,
                false, writeTypeIdFalse, writeTypeIdFalse);

        // new Boolean(false) is a separate instance from Boolean.FALSE (reference inequality),
        // so withWriteTypeIdForDefaultImpl must create and return a new Value object
        Boolean anotherFalseInstance = new Boolean(false);
        JsonTypeInfo.Value updatedValue = baseValue.withWriteTypeIdForDefaultImpl(anotherFalseInstance);

        // Both values carry the same logical data, so equals() must return true
        boolean valuesAreEqual = baseValue.equals(updatedValue);
        assertTrue(valuesAreEqual);

        // A new instance was created because the Boolean references differed
        assertNotSame(updatedValue, baseValue);

        // shouldWriteTypeIdForDefaultImpl() returns false when the flag is Boolean.FALSE
        assertFalse(updatedValue.shouldWriteTypeIdForDefaultImpl());
    }
}
