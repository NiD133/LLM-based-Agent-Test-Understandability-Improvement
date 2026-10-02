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
public class JsonTypeInfo_ESTest_test12 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        JsonTypeInfo.Id idType = JsonTypeInfo.Id.MINIMAL_CLASS;
        JsonTypeInfo.As inclusionType = JsonTypeInfo.As.WRAPPER_ARRAY;
        String explicitPropertyName = "-HHcn77L=.C2";
        Class<Object> defaultImplementation = Object.class;
        Boolean requireTypeIdForSubtypes = Boolean.TRUE;
        Boolean writeTypeIdForDefaultImpl = Boolean.TRUE;

        JsonTypeInfo.Value value = JsonTypeInfo.Value.construct(
                idType,
                inclusionType,
                explicitPropertyName,
                defaultImplementation,
                false,
                requireTypeIdForSubtypes,
                writeTypeIdForDefaultImpl);

        boolean enabled = JsonTypeInfo.Value.isEnabled(value);

        assertEquals(explicitPropertyName, value.getPropertyName());
        assertTrue(enabled);
        assertFalse(value.getIdVisible());
    }
}
