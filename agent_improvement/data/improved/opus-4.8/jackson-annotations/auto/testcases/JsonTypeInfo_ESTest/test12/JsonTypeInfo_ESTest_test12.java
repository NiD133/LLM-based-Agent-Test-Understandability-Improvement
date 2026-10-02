package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test12 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that a fully-specified {@link JsonTypeInfo.Value} preserves its
     * explicit property name, reports polymorphic handling as enabled (since the
     * id type is neither {@code null} nor {@code NONE} and the inclusion type is
     * not {@code NOTHING}), and exposes the configured id-visibility flag.
     */
    @Test(timeout = 4000)
    public void constructedValueReportsEnabledAndKeepsConfiguration() throws Throwable {
        String explicitPropertyName = "-HHcn77L=.C2";
        boolean idNotVisible = false;
        Boolean requireTypeIdForSubtypes = Boolean.TRUE;
        Boolean writeTypeIdForDefaultImpl = Boolean.TRUE;

        JsonTypeInfo.Value value = JsonTypeInfo.Value.construct(
                JsonTypeInfo.Id.MINIMAL_CLASS,
                JsonTypeInfo.As.WRAPPER_ARRAY,
                explicitPropertyName,
                Object.class,
                idNotVisible,
                requireTypeIdForSubtypes,
                writeTypeIdForDefaultImpl);

        assertTrue("polymorphic handling should be enabled for MINIMAL_CLASS + WRAPPER_ARRAY",
                JsonTypeInfo.Value.isEnabled(value));
        assertEquals("explicit property name should be retained",
                explicitPropertyName, value.getPropertyName());
        assertFalse("id should not be visible", value.getIdVisible());
    }
}
