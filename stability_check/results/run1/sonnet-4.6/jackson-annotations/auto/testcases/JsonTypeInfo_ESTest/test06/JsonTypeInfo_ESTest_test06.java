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
     * Verifies that withWriteTypeIdForDefaultImpl creates a new Value instance when
     * the replacement Boolean is a distinct object (even if equal by value).
     *
     * The Value implementation uses reference equality (==) for this field, so
     * passing a new Boolean(false) — a different object from the cached Boolean.FALSE
     * stored in the original — triggers a copy. The copy is logically equal to the
     * original (same field values) but is not the same object reference.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // Boolean.valueOf of any non-"true" string returns the cached Boolean.FALSE
        Boolean falseFromCache = Boolean.valueOf("-0VzDY5^*");

        JsonTypeInfo.Value original = new JsonTypeInfo.Value(
                JsonTypeInfo.Id.NONE,
                JsonTypeInfo.As.WRAPPER_OBJECT,
                "-0VzDY5^*",
                Integer.class,
                false,
                falseFromCache,
                falseFromCache);

        // new Boolean(false) is a fresh object, not the same reference as Boolean.FALSE,
        // so the identity check inside withWriteTypeIdForDefaultImpl fails and a new
        // Value is returned even though the boolean value is unchanged
        Boolean distinctFalseObject = new Boolean(false);
        JsonTypeInfo.Value updated = original.withWriteTypeIdForDefaultImpl(distinctFalseObject);

        assertTrue(original.equals(updated));
        assertNotSame(updated, original);
        assertFalse(updated.shouldWriteTypeIdForDefaultImpl());
    }
}
