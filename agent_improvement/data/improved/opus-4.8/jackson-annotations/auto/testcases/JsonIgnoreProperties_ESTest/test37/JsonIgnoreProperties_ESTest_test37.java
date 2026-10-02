package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test37 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Builds a Value from an annotation whose flags are all false and whose
     * value() is null, then verifies that:
     *   - equals() against an unrelated plain Object returns false, and
     *   - the resulting Value exposes the expected false/false flags
     *     (merge is forced to false by Value.from()).
     */
    @Test(timeout = 4000)
    public void valueFromAllFalseAnnotationDoesNotEqualPlainObject() throws Throwable {
        // Arrange: an annotation mock reporting all-false flags and a null property list.
        JsonIgnoreProperties annotation = mock(JsonIgnoreProperties.class, CALLS_REAL_METHODS);
        doReturn(false).when(annotation).allowGetters();
        doReturn(false).when(annotation).allowSetters();
        doReturn(false).when(annotation).ignoreUnknown();
        doReturn((String[]) null).when(annotation).value();

        JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.from(annotation);

        // Act & Assert: a Value never equals an arbitrary non-Value object.
        boolean equalsPlainObject = value.equals(new Object());
        assertFalse(equalsPlainObject);

        // Value.from() always sets merge=false, and the annotation flags are all false.
        assertFalse(value.getMerge());
        assertFalse(value.getIgnoreUnknown());
        assertFalse(value.getAllowGetters());
        assertFalse(value.getAllowSetters());
    }
}
