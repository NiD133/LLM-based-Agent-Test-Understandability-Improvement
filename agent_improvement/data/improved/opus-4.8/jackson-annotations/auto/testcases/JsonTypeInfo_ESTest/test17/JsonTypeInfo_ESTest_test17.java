package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test17 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Builds a JsonTypeInfo.Value via the 7-argument construct() factory and verifies that the
     * supplied property name, id-visibility flag, and write-type-id behaviour are reflected back
     * through the corresponding accessors.
     */
    @Test(timeout = 4000)
    public void constructStoresPropertyNameVisibilityAndWriteTypeIdFlag() throws Throwable {
        JsonTypeInfo.Id idType = JsonTypeInfo.Id.SIMPLE_NAME;
        JsonTypeInfo.As inclusionType = JsonTypeInfo.As.WRAPPER_OBJECT;
        String propertyName = "WRAPPER_ARRAY";
        boolean idVisible = true;
        Boolean writeTypeIdForDefaultImpl = Boolean.TRUE;

        JsonTypeInfo.Value value = JsonTypeInfo.Value.construct(
                idType,
                inclusionType,
                propertyName,
                Object.class,
                idVisible,
                /* requireTypeIdForSubtypes */ Boolean.TRUE,
                writeTypeIdForDefaultImpl);

        // writeTypeIdForDefaultImpl was set to TRUE, so the type id should be written.
        assertTrue(value.shouldWriteTypeIdForDefaultImpl());
        // Explicit property name and visibility flag are retained as given.
        assertEquals("WRAPPER_ARRAY", value.getPropertyName());
        assertTrue(value.getIdVisible());
    }
}
