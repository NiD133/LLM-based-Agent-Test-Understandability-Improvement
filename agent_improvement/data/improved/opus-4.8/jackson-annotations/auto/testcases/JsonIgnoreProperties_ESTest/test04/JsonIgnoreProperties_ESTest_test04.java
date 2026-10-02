package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test04 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that rebuilding a {@link JsonIgnoreProperties.Value} with the same
     * ignored-property set it already holds yields an equal Value, and that a Value
     * derived from an annotation has merging disabled.
     */
    @Test(timeout = 4000)
    public void rebuildingValueWithSameIgnoredSetIsEqualAndDoesNotMerge() throws Throwable {
        // Build an annotation whose flags are all false and whose value() list is null,
        // which is equivalent to the "empty" configuration.
        JsonIgnoreProperties annotation = mock(JsonIgnoreProperties.class, CALLS_REAL_METHODS);
        doReturn(false).when(annotation).allowGetters();
        doReturn(false).when(annotation).allowSetters();
        doReturn(false).when(annotation).ignoreUnknown();
        doReturn((String[]) null).when(annotation).value();

        // Derive a Value from the annotation; Value.from() always sets merge=false.
        JsonIgnoreProperties.Value valueFromAnnotation = JsonIgnoreProperties.Value.from(annotation);

        // With allowGetters=false, the serialization-ignored set is just the (empty) ignored set.
        Set<String> ignoredForSerialization = valueFromAnnotation.findIgnoredForSerialization();

        // Rebuilding with the very same ignored set should produce an equal Value.
        JsonIgnoreProperties.Value rebuiltValue = valueFromAnnotation.withIgnored(ignoredForSerialization);

        assertTrue(valueFromAnnotation.equals(rebuiltValue));
        assertFalse(rebuiltValue.getMerge());
    }
}
