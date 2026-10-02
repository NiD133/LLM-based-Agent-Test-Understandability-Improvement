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
     * Verifies that withWriteTypeIdForDefaultImpl produces a new but equal Value instance
     * when called with a different Boolean object holding the same false value.
     *
     * The implementation uses reference equality (==) to decide whether to return 'this'
     * or a new instance. Passing a distinct Boolean.FALSE object always triggers a new
     * instance. The resulting instance is logically equal to the original (equals returns
     * true) but is not the same object (assertNotSame), and shouldWriteTypeIdForDefaultImpl
     * returns false because writeTypeIdForDefaultImpl is Boolean.FALSE.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // Boolean.valueOf of a non-"true" string always yields Boolean.FALSE
        Boolean writeTypeIdFlag = Boolean.valueOf("-0VzDY5^*");

        JsonTypeInfo.Value original = new JsonTypeInfo.Value(
                JsonTypeInfo.Id.NONE,
                JsonTypeInfo.As.WRAPPER_OBJECT,
                "-0VzDY5^*",
                Integer.class,
                false,
                writeTypeIdFlag,
                writeTypeIdFlag);

        // new Boolean(false) is a distinct object from Boolean.FALSE, so reference equality
        // check inside withWriteTypeIdForDefaultImpl will not short-circuit and a new
        // Value instance is returned even though the logical value is the same.
        Boolean differentFalseObject = new Boolean(false);
        JsonTypeInfo.Value updated = original.withWriteTypeIdForDefaultImpl(differentFalseObject);

        // The two instances are logically equal because all field values match
        assertTrue(original.equals(updated));

        // But they are not the same object due to the reference-equality short-circuit miss
        assertNotSame(updated, original);

        // writeTypeIdForDefaultImpl is false, so shouldWriteTypeIdForDefaultImpl() returns false
        assertFalse(updated.shouldWriteTypeIdForDefaultImpl());
    }
}
