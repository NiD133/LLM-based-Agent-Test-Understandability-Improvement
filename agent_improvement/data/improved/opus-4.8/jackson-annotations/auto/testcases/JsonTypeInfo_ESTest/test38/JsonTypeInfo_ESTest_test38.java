package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test38 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Builds a JsonTypeInfo.Value via construct() with idVisible=true and
     * writeTypeIdForDefaultImpl=FALSE, then verifies the corresponding accessors:
     *  - getIdVisible() reflects the idVisible argument (true).
     *  - shouldWriteTypeIdForDefaultImpl() is false because the flag is explicitly FALSE.
     */
    @Test(timeout = 4000)
    public void constructWithVisibleIdAndFalseWriteFlag_exposesExpectedAccessors() throws Throwable {
        // valueFor() always returns the JsonTypeInfo annotation class; reuse it as the defaultImpl.
        Class<JsonTypeInfo> defaultImpl = JsonTypeInfo.Value.EMPTY.valueFor();

        boolean idVisible = true;
        Boolean writeTypeIdForDefaultImpl = Boolean.FALSE; // explicit FALSE -> shouldWriteTypeIdForDefaultImpl() == false
        Boolean requireTypeIdForSubtypes = Boolean.FALSE;

        JsonTypeInfo.Value value = JsonTypeInfo.Value.construct(
                JsonTypeInfo.Id.NONE,
                JsonTypeInfo.As.WRAPPER_ARRAY,
                "",
                defaultImpl,
                idVisible,
                requireTypeIdForSubtypes,
                writeTypeIdForDefaultImpl);

        assertTrue(value.getIdVisible());
        assertFalse(value.shouldWriteTypeIdForDefaultImpl());
    }
}
