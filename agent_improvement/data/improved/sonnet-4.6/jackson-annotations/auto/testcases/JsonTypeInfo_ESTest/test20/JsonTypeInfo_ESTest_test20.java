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
public class JsonTypeInfo_ESTest_test20 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that withWriteTypeIdForDefaultImpl(false) produces a distinct Value:
     * - The returned Value is not equal to EMPTY (which has writeTypeIdForDefaultImpl=null).
     * - The returned Value still has idVisible=false (unchanged from EMPTY).
     * - Equality is not symmetric: neither Value equals the other.
     */
    @Test(timeout = 4000)
    public void test20() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        // EMPTY has writeTypeIdForDefaultImpl=null; passing false creates a new, distinct instance
        JsonTypeInfo.Value valueWithWriteTypeIdFalse = emptyValue.withWriteTypeIdForDefaultImpl(Boolean.valueOf(false));

        // idVisible is not affected by withWriteTypeIdForDefaultImpl — it stays false
        assertFalse(valueWithWriteTypeIdFalse.getIdVisible());

        // The two values differ in writeTypeIdForDefaultImpl (null vs false), so they are not equal
        assertFalse(valueWithWriteTypeIdFalse.equals(emptyValue));
        assertFalse(emptyValue.equals((Object) valueWithWriteTypeIdFalse));
    }
}
