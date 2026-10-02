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
     * Verifies that {@link JsonIncludeProperties.Value#withOverrides} produces a
     * brand-new Value whenever the two property sets being merged do not overlap.
     *
     * The base Value is derived from an annotation whose only included property is
     * a single {@code null} entry, while the override Value includes a distinct
     * non-null property name. Since the two sets share no common name, the merged
     * result keeps neither set verbatim and is therefore not equal (nor identical)
     * to either input Value.
     */
    @Test(timeout = 4000)
    public void mergingDisjointPropertySetsYieldsDistinctValue() throws Throwable {
        // Override Value: includes one concrete property name, ordering unspecified.
        LinkedHashSet<String> overrideNames = new LinkedHashSet<String>();
        overrideNames.add("$h]54GG#Ke5AZNb`7r");
        JsonIncludeProperties.Value overrideValue =
                new JsonIncludeProperties.Value(overrideNames, (Boolean) null);

        // Mock annotation whose value() is a single null entry and default ordering.
        String[] baseNames = new String[1]; // length 1, element is null
        JsonIncludeProperties baseAnnotation = mock(JsonIncludeProperties.class, CALLS_REAL_METHODS);
        doReturn(OptBoolean.DEFAULT).when(baseAnnotation).order();
        doReturn(baseNames).when(baseAnnotation).value();

        // Base Value built from the mocked annotation: included set is {null}.
        JsonIncludeProperties.Value baseValue = JsonIncludeProperties.Value.from(baseAnnotation);

        // Merge: the two included sets are disjoint, so the intersection is empty.
        JsonIncludeProperties.Value mergedValue = baseValue.withOverrides(overrideValue);

        // The merged Value matches neither input, by value or by reference.
        assertFalse(mergedValue.equals((Object) overrideValue));
        assertNotSame(mergedValue, overrideValue);
        assertFalse(mergedValue.equals((Object) baseValue));
        assertNotSame(mergedValue, baseValue);
    }
}
