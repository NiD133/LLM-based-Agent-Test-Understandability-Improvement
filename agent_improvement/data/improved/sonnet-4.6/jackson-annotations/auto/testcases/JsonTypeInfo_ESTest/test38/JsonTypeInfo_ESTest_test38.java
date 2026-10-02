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
     * Verifies that a Value constructed with idVisible=true and writeTypeIdForDefaultImpl=false
     * correctly reports those settings via getIdVisible() and shouldWriteTypeIdForDefaultImpl().
     *
     * Note: defaultImpl is obtained via EMPTY.valueFor(), which returns JsonTypeInfo.class
     * (an annotation type). The construct() method treats annotation types as null for defaultImpl.
     */
    @Test(timeout = 4000)
    public void test38() throws Throwable {
        // valueFor() on any Value instance returns JsonTypeInfo.class (the annotation type)
        Class<JsonTypeInfo> defaultImplClass = JsonTypeInfo.Value.EMPTY.valueFor();

        JsonTypeInfo.Value value = JsonTypeInfo.Value.construct(
            JsonTypeInfo.Id.NONE,
            JsonTypeInfo.As.WRAPPER_ARRAY,
            /* propertyName */ "",
            /* defaultImpl */ defaultImplClass,
            /* idVisible */ true,
            /* requireTypeIdForSubtypes */ Boolean.FALSE,
            /* writeTypeIdForDefaultImpl */ Boolean.FALSE
        );

        // idVisible was set to true, so getIdVisible() should return true
        assertTrue(value.getIdVisible());
        // writeTypeIdForDefaultImpl was set to false, so shouldWriteTypeIdForDefaultImpl() returns false
        assertFalse(value.shouldWriteTypeIdForDefaultImpl());
    }
}
