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
public class JsonTypeInfo_ESTest_test01 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that withRequireTypeIdForSubtypes(false) produces a Value that differs
     * from EMPTY (which has requireTypeIdForSubtypes=null), while both retain the
     * default idVisible=false and writeTypeIdForDefaultImpl=null (shouldWrite returns true).
     */
    @Test(timeout = 4000)
    public void test01_withRequireTypeIdForSubtypesFalse_notEqualToEmpty() throws Throwable {
        // EMPTY has requireTypeIdForSubtypes=null; setting it to false creates a distinct Value
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;
        JsonTypeInfo.Value valueWithRequireTypeIdFalse = emptyValue.withRequireTypeIdForSubtypes(Boolean.FALSE);

        // The two values differ only in requireTypeIdForSubtypes (null vs. false), so they are not equal
        boolean newValueEqualsEmpty = valueWithRequireTypeIdFalse.equals(emptyValue);
        assertFalse(valueWithRequireTypeIdFalse.getIdVisible());
        assertFalse(emptyValue.equals((Object) valueWithRequireTypeIdFalse));

        // writeTypeIdForDefaultImpl is still null (inherited from EMPTY), so shouldWrite defaults to true
        assertTrue(valueWithRequireTypeIdFalse.shouldWriteTypeIdForDefaultImpl());
        assertFalse(newValueEqualsEmpty);
    }
}
