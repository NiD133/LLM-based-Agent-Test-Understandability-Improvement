package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test31 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies how {@link JsonTypeInfo.Value#construct} derives the property name
     * and the type-id visibility / default-impl write behaviour.
     *
     * <p>Key behaviours exercised:
     * <ul>
     *   <li>A {@code null} property name falls back to the default property name of the
     *       {@link JsonTypeInfo.Id} ({@code MINIMAL_CLASS} -> "@c").</li>
     *   <li>{@code idVisible} is stored and returned as-is (here {@code true}).</li>
     *   <li>A {@code null} {@code writeTypeIdForDefaultImpl} means the type id should
     *       still be written for the default impl.</li>
     * </ul>
     */
    @Test(timeout = 4000)
    public void constructWithNullPropertyName_usesIdDefaultName() throws Throwable {
        JsonTypeInfo.Value value = JsonTypeInfo.Value.construct(
                JsonTypeInfo.Id.MINIMAL_CLASS,   // idType -> default property name "@c"
                JsonTypeInfo.As.NOTHING,         // inclusionType
                (String) null,                   // propertyName -> falls back to id default
                Integer.class,                   // defaultImpl
                true,                            // idVisible
                Boolean.FALSE,                   // requireTypeIdForSubtypes
                (Boolean) null);                 // writeTypeIdForDefaultImpl

        assertEquals("@c", value.getPropertyName());
        assertTrue(value.getIdVisible());
        assertTrue(value.shouldWriteTypeIdForDefaultImpl());
    }
}
