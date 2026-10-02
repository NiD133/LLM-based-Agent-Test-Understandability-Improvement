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
     * Verifies that withOverrides computes the intersection of included property sets,
     * and that the merged result is distinct from both the override and the base values.
     *
     * Setup:
     *   overrideValue  — includes one real property name, no ordering defined
     *   baseValue      — built from a mock annotation whose value() returns a single-element
     *                    array containing null, so its included set is {null}
     *
     * withOverrides computes: {null} ∩ {"$h]54GG#Ke5AZNb`7r"} = {} (empty set)
     * The merged result therefore differs from both overrideValue and baseValue.
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        // overrideValue: includes one specific property name, ordered=null
        LinkedHashSet<String> overrideProperties = new LinkedHashSet<String>();
        overrideProperties.add("$h]54GG#Ke5AZNb`7r");
        JsonIncludeProperties.Value overrideValue =
                new JsonIncludeProperties.Value(overrideProperties, (Boolean) null);

        // Mock annotation: order() returns DEFAULT (resolves to null via asBoolean()),
        // value() returns a one-element array containing null → included = {null}
        OptBoolean defaultOrder = OptBoolean.DEFAULT;
        String[] singleNullElement = new String[1];
        JsonIncludeProperties mockAnnotation =
                mock(JsonIncludeProperties.class, CALLS_REAL_METHODS);
        doReturn(defaultOrder).when(mockAnnotation).order();
        doReturn(singleNullElement).when(mockAnnotation).value();

        // baseValue: derived from the mock annotation, included = {null}, ordered = null
        JsonIncludeProperties.Value baseValue =
                JsonIncludeProperties.Value.from(mockAnnotation);

        // mergedValue: intersection of {null} and {"$h]54GG#Ke5AZNb`7r"} = {} (empty)
        JsonIncludeProperties.Value mergedValue =
                baseValue.withOverrides(overrideValue);

        // The merged result must differ from both inputs
        assertFalse(mergedValue.equals((Object) overrideValue));
        assertNotSame(mergedValue, overrideValue);
        assertFalse(mergedValue.equals((Object) baseValue));
        assertNotSame(mergedValue, baseValue);
    }
}
