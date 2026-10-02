package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test27 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Deriving a Value from the EMPTY template by setting only the id type
     * keeps polymorphic handling disabled: isEnabled() additionally requires a
     * non-NOTHING inclusion type, which EMPTY does not have. The new id type is
     * still recorded, while id visibility stays at its default of false.
     */
    @Test(timeout = 4000)
    public void settingIdTypeOnEmptyValueDoesNotEnablePolymorphicHandling() throws Throwable {
        JsonTypeInfo.Value valueWithIdType =
                JsonTypeInfo.Value.EMPTY.withIdType(JsonTypeInfo.Id.MINIMAL_CLASS);

        boolean polymorphicHandlingEnabled = JsonTypeInfo.Value.isEnabled(valueWithIdType);

        assertFalse(polymorphicHandlingEnabled);
        assertEquals(JsonTypeInfo.Id.MINIMAL_CLASS, valueWithIdType.getIdType());
        assertFalse(valueWithIdType.getIdVisible());
    }
}
