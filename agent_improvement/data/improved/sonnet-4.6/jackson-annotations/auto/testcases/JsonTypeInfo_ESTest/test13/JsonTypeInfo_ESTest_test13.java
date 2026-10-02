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
public class JsonTypeInfo_ESTest_test13 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that a JsonTypeInfo.Value with a null idType is not considered enabled,
     * and that false flags for idVisible and writeTypeIdForDefaultImpl are reflected correctly.
     */
    @Test(timeout = 4000)
    public void test13() throws Throwable {
        // Arrange: build a Value with null idType, PROPERTY inclusion, and both
        // requireTypeIdForSubtypes and writeTypeIdForDefaultImpl set to FALSE
        JsonTypeInfo.As inclusionType = JsonTypeInfo.As.PROPERTY;
        Class<Object> defaultImpl = Object.class;
        Boolean falseFlag = Boolean.FALSE;
        JsonTypeInfo.Value value = new JsonTypeInfo.Value(
                (JsonTypeInfo.Id) null, inclusionType, "M8", defaultImpl,
                /*idVisible=*/ false, falseFlag, falseFlag);

        // Act: check whether polymorphic type handling is enabled
        boolean enabled = JsonTypeInfo.Value.isEnabled(value);

        // Assert: null idType means polymorphic handling is disabled
        assertFalse(value.getIdVisible());
        assertFalse(enabled);
        // writeTypeIdForDefaultImpl=FALSE means type id should NOT be written for the default impl
        assertFalse(value.shouldWriteTypeIdForDefaultImpl());
    }
}
