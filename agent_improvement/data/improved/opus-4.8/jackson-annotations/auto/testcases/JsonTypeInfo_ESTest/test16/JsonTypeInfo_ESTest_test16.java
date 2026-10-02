package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test16 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that when a JsonTypeInfo.Value is constructed with
     * writeTypeIdForDefaultImpl explicitly set to FALSE and idVisible set to
     * false, the corresponding accessors report those exact values.
     */
    @Test(timeout = 4000)
    public void constructedValueReflectsWriteTypeIdAndVisibilityFlags() throws Throwable {
        boolean idVisible = false;
        Boolean writeTypeIdForDefaultImpl = Boolean.FALSE;

        JsonTypeInfo.Value value = JsonTypeInfo.Value.construct(
                JsonTypeInfo.Id.CUSTOM,
                JsonTypeInfo.As.NOTHING,
                "G",                       // property name
                Object.class,              // default implementation
                idVisible,
                Boolean.FALSE,             // requireTypeIdForSubtypes
                writeTypeIdForDefaultImpl);

        assertFalse("type id should not be written for default impl when flag is FALSE",
                value.shouldWriteTypeIdForDefaultImpl());
        assertFalse("id should not be visible when idVisible is false",
                value.getIdVisible());
    }
}
