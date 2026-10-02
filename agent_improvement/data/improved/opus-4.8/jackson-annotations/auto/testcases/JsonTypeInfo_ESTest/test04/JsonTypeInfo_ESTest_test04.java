package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test04 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonTypeInfo.Value#withInclusionType} produces a new,
     * distinct Value: changing the inclusion type to WRAPPER_ARRAY yields a Value
     * that is no longer equal to the original EMPTY value (in either direction),
     * while leaving the unrelated {@code idVisible} flag at its default of false.
     */
    @Test(timeout = 4000)
    public void changingInclusionTypeYieldsUnequalValue() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        JsonTypeInfo.Value wrapperArrayValue =
                emptyValue.withInclusionType(JsonTypeInfo.As.WRAPPER_ARRAY);

        // The inclusion type was the only change; idVisible keeps its default.
        assertFalse(wrapperArrayValue.getIdVisible());

        // The two values differ by inclusion type, so equality fails both ways.
        assertFalse(wrapperArrayValue.equals(emptyValue));
        assertFalse(emptyValue.equals(wrapperArrayValue));
    }
}
