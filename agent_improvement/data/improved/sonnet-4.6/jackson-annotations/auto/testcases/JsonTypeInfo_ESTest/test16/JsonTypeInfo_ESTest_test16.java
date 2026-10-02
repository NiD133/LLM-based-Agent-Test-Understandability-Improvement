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
public class JsonTypeInfo_ESTest_test16 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that when writeTypeIdForDefaultImpl is explicitly set to FALSE,
     * shouldWriteTypeIdForDefaultImpl() returns false; also confirms idVisible is false.
     */
    @Test(timeout = 4000)
    public void test16() throws Throwable {
        JsonTypeInfo.Id idType = JsonTypeInfo.Id.CUSTOM;
        JsonTypeInfo.As inclusionType = JsonTypeInfo.As.NOTHING;
        Class<Object> defaultImpl = Object.class;
        Boolean writeTypeIdForDefaultImpl = Boolean.FALSE;
        Boolean requireTypeIdForSubtypes = Boolean.FALSE;

        JsonTypeInfo.Value typeInfoValue = JsonTypeInfo.Value.construct(
                idType, inclusionType, "G", defaultImpl,
                /*idVisible=*/ false, requireTypeIdForSubtypes, writeTypeIdForDefaultImpl);

        // writeTypeIdForDefaultImpl=FALSE means the type id should NOT be written for the default impl
        boolean shouldWriteTypeId = typeInfoValue.shouldWriteTypeIdForDefaultImpl();
        assertFalse(shouldWriteTypeId);

        // idVisible=false means the type id property is not exposed to deserializers
        assertFalse(typeInfoValue.getIdVisible());
    }
}
