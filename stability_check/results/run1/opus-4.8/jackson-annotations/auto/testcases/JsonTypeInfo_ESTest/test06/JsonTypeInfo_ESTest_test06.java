package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test06 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that calling {@code withWriteTypeIdForDefaultImpl} with a Boolean
     * that is equal in value but is a *different* object reference returns a brand
     * new (yet logically equal) Value instance, and that the resulting flag reads
     * as {@code false}.
     */
    @Test(timeout = 4000)
    public void withWriteTypeIdForDefaultImpl_differentFalseInstance_returnsEqualButDistinctValue() throws Throwable {
        // Boolean.valueOf of a non-"true" string yields the cached Boolean.FALSE.
        Boolean cachedFalse = Boolean.valueOf("-0VzDY5^*");

        // Build a Value whose writeTypeIdForDefaultImpl flag is the cached Boolean.FALSE.
        JsonTypeInfo.Value originalValue = new JsonTypeInfo.Value(
                JsonTypeInfo.Id.NONE,
                JsonTypeInfo.As.WRAPPER_OBJECT,
                "-0VzDY5^*",          // property name
                Integer.class,         // defaultImpl
                false,                 // idVisible
                cachedFalse,           // requireTypeIdForSubtypes
                cachedFalse);          // writeTypeIdForDefaultImpl

        // A separately-allocated Boolean.FALSE: equal in value, but a distinct reference.
        Boolean freshFalse = new Boolean(false);

        // Because the reference differs from the stored one, a new Value is created.
        JsonTypeInfo.Value updatedValue = originalValue.withWriteTypeIdForDefaultImpl(freshFalse);

        // The two Values are equal by value (both flags are false)...
        assertTrue(originalValue.equals(updatedValue));
        // ...but they are distinct objects.
        assertNotSame(updatedValue, originalValue);
        // A false flag means the type id should not be written for the default impl.
        assertFalse(updatedValue.shouldWriteTypeIdForDefaultImpl());
    }
}
