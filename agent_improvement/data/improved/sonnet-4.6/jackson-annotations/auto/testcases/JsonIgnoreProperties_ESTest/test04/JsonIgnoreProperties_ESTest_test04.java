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
     * Verifies that a Value built from an annotation with a null property list and all
     * boolean flags set to false is equal to a copy produced by withIgnored() using the
     * same (empty) ignored-for-serialization set, and that neither copy enables merging.
     *
     * Key behaviours exercised:
     *  - Value.from() treats a null value() array as an empty ignored-property set.
     *  - findIgnoredForSerialization() returns the ignored set when allowGetters is false.
     *  - withIgnored(Set) preserves all other flags, so the resulting Value is equal to the original.
     *  - The merge flag is false because Value.from() always sets merge=false.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // Arrange: mock the annotation with all-false flags and a null property list.
        JsonIgnoreProperties annotation = mock(JsonIgnoreProperties.class, CALLS_REAL_METHODS);
        doReturn(false).when(annotation).allowGetters();
        doReturn(false).when(annotation).allowSetters();
        doReturn(false).when(annotation).ignoreUnknown();
        doReturn((String[]) null).when(annotation).value();

        // Build a Value from the mocked annotation.
        // Because value() returns null, the ignored-property set is empty.
        // Because allowGetters() is false, findIgnoredForSerialization() returns that empty set.
        // Value.from() always uses merge=false, so getMerge() will be false on the result.
        JsonIgnoreProperties.Value originalValue = JsonIgnoreProperties.Value.from(annotation);

        // Act: obtain the ignored-for-serialization set and create a copy with the same set.
        Set<String> ignoredForSerialization = originalValue.findIgnoredForSerialization();
        JsonIgnoreProperties.Value copiedValue = originalValue.withIgnored(ignoredForSerialization);

        // Assert: the copy is equal to the original (same ignored set and same flags),
        // and neither Value has merge enabled.
        boolean valuesAreEqual = originalValue.equals(copiedValue);
        assertTrue(valuesAreEqual);
        assertFalse(copiedValue.getMerge());
    }
}
