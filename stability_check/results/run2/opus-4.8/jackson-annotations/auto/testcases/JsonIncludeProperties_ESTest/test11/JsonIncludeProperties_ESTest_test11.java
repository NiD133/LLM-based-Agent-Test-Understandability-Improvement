package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test11 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * Verifies that merging two {@link JsonIncludeProperties.Value} instances via
     * {@code withOverrides} yields a brand-new Value that is distinct from both inputs
     * when the two included-property sets share no common entries.
     *
     * <p>The override Value includes a single named property, while the base Value
     * (built from a mocked annotation) includes a completely different property. Since
     * {@code withOverrides} keeps only the intersection of the two sets, the result has
     * an empty included set and therefore does not equal either source Value.
     */
    @Test(timeout = 4000)
    public void mergeWithNonOverlappingIncludesProducesDistinctValue() throws Throwable {
        // Override Value: includes exactly one (arbitrary) property name, order unspecified.
        LinkedHashSet<String> overrideIncludedNames = new LinkedHashSet<String>();
        overrideIncludedNames.add("$h]54GG#Ke5AZNb`7r");
        JsonIncludeProperties.Value overrideValue =
                new JsonIncludeProperties.Value(overrideIncludedNames, (Boolean) null);

        // Base Value: built from a mocked annotation whose value() is a single-element
        // (null-containing) array and whose order() is the default (undefined) setting.
        String[] annotationValueNames = new String[1];
        JsonIncludeProperties includePropertiesAnnotation =
                mock(JsonIncludeProperties.class, CALLS_REAL_METHODS);
        doReturn(OptBoolean.DEFAULT).when(includePropertiesAnnotation).order();
        doReturn(annotationValueNames).when(includePropertiesAnnotation).value();
        JsonIncludeProperties.Value baseValue =
                JsonIncludeProperties.Value.from(includePropertiesAnnotation);

        // Merge: the intersection of the two include sets is empty.
        JsonIncludeProperties.Value mergedValue = baseValue.withOverrides(overrideValue);

        // The merged Value is a new, distinct object from both the override and the base.
        assertFalse(mergedValue.equals((Object) overrideValue));
        assertNotSame(mergedValue, overrideValue);
        assertFalse(mergedValue.equals((Object) baseValue));
        assertNotSame(mergedValue, baseValue);
    }
}
