package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Value;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test13 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies that overriding a Value where every accessor is NONE with a
     * Value that only sets the scalar-constructor visibility (leaving all
     * other accessors at DEFAULT) returns the original instance unchanged.
     *
     * Because DEFAULT in the override means "keep the base value", and the
     * base is already NONE for every accessor, withOverrides() produces a
     * Value equal to the base and therefore returns the base itself.
     */
    @Test(timeout = 4000)
    public void overrideWithDefaultsExceptScalarConstructorReturnsSameBase() throws Throwable {
        // Base Value: every accessor explicitly set to NONE.
        Value allNone = Value.construct(
                Visibility.NONE, Visibility.NONE, Visibility.NONE,
                Visibility.NONE, Visibility.NONE, Visibility.NONE);

        // Override Value: only the scalar-constructor accessor is set (to NONE);
        // every other accessor stays at DEFAULT.
        Value scalarConstructorOnly =
                Value.construct(PropertyAccessor.SCALAR_CONSTRUCTOR, Visibility.NONE);

        Value merged = allNone.withOverrides(scalarConstructorOnly);

        // The override-only Value keeps DEFAULT for all non-scalar accessors...
        assertEquals(Visibility.DEFAULT, scalarConstructorOnly.getGetterVisibility());
        assertEquals(Visibility.DEFAULT, scalarConstructorOnly.getFieldVisibility());
        assertEquals(Visibility.DEFAULT, scalarConstructorOnly.getIsGetterVisibility());
        assertEquals(Visibility.DEFAULT, scalarConstructorOnly.getSetterVisibility());
        // ...and NONE only for the scalar constructor it explicitly configured.
        assertEquals(Visibility.NONE, scalarConstructorOnly.getScalarConstructorVisibility());

        // Merging produced no actual change, so the base instance is returned as-is.
        assertSame(allNone, merged);
    }
}
