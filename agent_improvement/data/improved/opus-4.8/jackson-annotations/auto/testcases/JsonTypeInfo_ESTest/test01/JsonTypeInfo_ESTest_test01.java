package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test01 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that {@code withRequireTypeIdForSubtypes(false)} produces a new,
     * distinct Value: deriving from EMPTY changes the "require type id" setting,
     * so the derived Value is no longer equal to EMPTY, while unrelated settings
     * (id visibility, writing type id for default impl) keep their EMPTY defaults.
     */
    @Test(timeout = 4000)
    public void withRequireTypeIdForSubtypes_createsValueNotEqualToEmpty() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        JsonTypeInfo.Value derivedValue =
                emptyValue.withRequireTypeIdForSubtypes(Boolean.FALSE);

        // Changing "require type id for subtypes" makes the derived Value differ from EMPTY,
        // and inequality holds in both directions.
        assertFalse(derivedValue.equals(emptyValue));
        assertFalse(emptyValue.equals(derivedValue));

        // Settings unaffected by this mutation retain their EMPTY defaults.
        assertFalse(derivedValue.getIdVisible());
        assertTrue(derivedValue.shouldWriteTypeIdForDefaultImpl());
    }
}
