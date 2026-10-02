package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test13 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies the behaviour of a {@link JsonTypeInfo.Value} that is built with a
     * {@code null} type id ({@link JsonTypeInfo.Id}) and with type-id writing for the
     * default implementation disabled.
     *
     * <ul>
     *   <li>{@code isEnabled} returns {@code false}: polymorphic handling requires a
     *       non-null id type, so a {@code null} id leaves the value disabled.</li>
     *   <li>{@code getIdVisible} returns {@code false}: matches the {@code idVisible}
     *       argument passed to the constructor.</li>
     *   <li>{@code shouldWriteTypeIdForDefaultImpl} returns {@code false}: matches the
     *       explicit {@code Boolean.FALSE} passed for that flag.</li>
     * </ul>
     */
    @Test(timeout = 4000)
    public void valueWithNullIdTypeIsNotEnabled() throws Throwable {
        // Construct a Value with: null id type, PROPERTY inclusion, property name "M8",
        // Object.class as default impl, id not visible, requireTypeIdForSubtypes=false,
        // and writeTypeIdForDefaultImpl=false.
        JsonTypeInfo.Value value = new JsonTypeInfo.Value(
                (JsonTypeInfo.Id) null,
                JsonTypeInfo.As.PROPERTY,
                "M8",
                Object.class,
                false,            // idVisible
                Boolean.FALSE,    // requireTypeIdForSubtypes
                Boolean.FALSE);   // writeTypeIdForDefaultImpl

        boolean enabled = JsonTypeInfo.Value.isEnabled(value);

        assertFalse("id type is null, so polymorphic handling must be disabled", enabled);
        assertFalse("id visibility was set to false", value.getIdVisible());
        assertFalse("writeTypeIdForDefaultImpl was set to false",
                value.shouldWriteTypeIdForDefaultImpl());
    }
}
