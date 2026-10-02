package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test11 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * Verifies that merging two Values whose included-property sets are disjoint
     * produces a brand-new Value that is distinct from both of its inputs.
     *
     * The override Value includes a single real property name, while the base
     * Value (built from a mocked annotation) includes a single {@code null} entry.
     * Because the two sets share no common element, {@code withOverrides} yields a
     * Value with an empty included set, which equals neither input.
     */
    @Test(timeout = 4000)
    public void mergingDisjointValuesYieldsValueDistinctFromBothInputs() throws Throwable {
        // Override Value: includes one concrete property name, ordering unspecified.
        LinkedHashSet<String> overrideIncluded = new LinkedHashSet<String>();
        overrideIncluded.add("$h]54GG#Ke5AZNb`7r");
        JsonIncludeProperties.Value overrideValue =
                new JsonIncludeProperties.Value(overrideIncluded, (Boolean) null);

        // Mock the annotation so its value() has a single (null) property and
        // its order() is the default; from(...) turns this into the base Value.
        String[] annotationProperties = new String[1]; // single null element
        JsonIncludeProperties annotation = mock(JsonIncludeProperties.class, CALLS_REAL_METHODS);
        doReturn(OptBoolean.DEFAULT).when(annotation).order();
        doReturn(annotationProperties).when(annotation).value();
        JsonIncludeProperties.Value baseValue = JsonIncludeProperties.Value.from(annotation);

        // Merge: base includes {null}, override includes {"$h]..."} -> no overlap.
        JsonIncludeProperties.Value mergedValue = baseValue.withOverrides(overrideValue);

        // The merged Value must differ from both the override and the base Values.
        assertFalse(mergedValue.equals((Object) overrideValue));
        assertNotSame(mergedValue, overrideValue);
        assertFalse(mergedValue.equals((Object) baseValue));
        assertNotSame(mergedValue, baseValue);
    }
}
