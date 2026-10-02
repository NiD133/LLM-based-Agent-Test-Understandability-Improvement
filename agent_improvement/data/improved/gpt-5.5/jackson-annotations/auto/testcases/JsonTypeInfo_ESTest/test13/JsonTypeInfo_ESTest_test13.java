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

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        JsonTypeInfo.As inclusionMode = JsonTypeInfo.As.PROPERTY;
        Class<Object> defaultImplementation = Object.class;
        Boolean disabledFlag = Boolean.FALSE;

        JsonTypeInfo.Value typeInfoValue = new JsonTypeInfo.Value(
                (JsonTypeInfo.Id) null,
                inclusionMode,
                "M8",
                defaultImplementation,
                false,
                disabledFlag,
                disabledFlag);

        boolean valueIsEnabled = JsonTypeInfo.Value.isEnabled(typeInfoValue);

        assertFalse("id visibility should remain disabled", typeInfoValue.getIdVisible());
        assertFalse("a value with no type id should not be enabled", valueIsEnabled);
        assertFalse("default implementation type id writing should remain disabled",
                typeInfoValue.shouldWriteTypeIdForDefaultImpl());
    }
}
