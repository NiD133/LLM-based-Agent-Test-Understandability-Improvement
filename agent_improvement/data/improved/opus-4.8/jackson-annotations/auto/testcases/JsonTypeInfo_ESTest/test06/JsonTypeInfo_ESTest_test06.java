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
     * Verifies that {@code withWriteTypeIdForDefaultImpl} returns a value that is
     * a distinct instance from the original yet still compares as equal, when the
     * new flag holds the same logical value but a different Boolean identity.
     *
     * The original value is built with a {@code Boolean.FALSE} (interned via
     * {@code Boolean.valueOf}). The mutator is then called with a freshly allocated
     * {@code new Boolean(false)}. Because {@code withWriteTypeIdForDefaultImpl}
     * compares the stored flag by reference (==), these two distinct Boolean
     * objects trigger creation of a new {@code Value}; however {@code equals}
     * compares by logical value, so the two values remain equal.
     */
    @Test(timeout = 4000)
    public void withWriteTypeIdForDefaultImpl_sameValueDifferentIdentity_isEqualButNotSame() throws Throwable {
        // Boolean.valueOf for any non-"true" string yields the interned Boolean.FALSE.
        Boolean internedFalse = Boolean.valueOf("-0VzDY5^*");

        JsonTypeInfo.Value originalValue = new JsonTypeInfo.Value(
                JsonTypeInfo.Id.NONE,
                JsonTypeInfo.As.WRAPPER_OBJECT,
                "-0VzDY5^*",            // property name
                Integer.class,          // default impl
                false,                  // id visible
                internedFalse,          // requireTypeIdForSubtypes
                internedFalse);         // writeTypeIdForDefaultImpl

        // A separate Boolean instance holding the same logical value (false).
        Boolean freshFalse = new Boolean(false);
        JsonTypeInfo.Value updatedValue = originalValue.withWriteTypeIdForDefaultImpl(freshFalse);

        // Different Boolean identity forces a new Value, but logical equality holds.
        assertTrue(originalValue.equals(updatedValue));
        assertNotSame(updatedValue, originalValue);

        // Flag value is false, so type id should not be written for the default impl.
        assertFalse(updatedValue.shouldWriteTypeIdForDefaultImpl());
    }
}
