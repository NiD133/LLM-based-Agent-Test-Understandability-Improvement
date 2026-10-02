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
public class JsonAutoDetect_ESTest_test04 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies that {@code withScalarConstructorVisibility} only changes the
     * scalar-constructor visibility, leaving every other accessor's visibility
     * untouched relative to the DEFAULT value.
     */
    @Test(timeout = 4000)
    public void withScalarConstructorVisibility_changesOnlyScalarConstructor() throws Throwable {
        Value updated = Value.DEFAULT.withScalarConstructorVisibility(Visibility.ANY);

        // The scalar-constructor visibility is updated to the supplied value...
        assertEquals(Visibility.ANY, updated.getScalarConstructorVisibility());

        // ...while every other accessor keeps the DEFAULT value's visibility.
        assertEquals(Visibility.PUBLIC_ONLY, updated.getCreatorVisibility());
        assertEquals(Visibility.PUBLIC_ONLY, updated.getIsGetterVisibility());
        assertEquals(Visibility.PUBLIC_ONLY, updated.getGetterVisibility());
        assertEquals(Visibility.PUBLIC_ONLY, updated.getFieldVisibility());
        assertEquals(Visibility.ANY, updated.getSetterVisibility());
    }
}
