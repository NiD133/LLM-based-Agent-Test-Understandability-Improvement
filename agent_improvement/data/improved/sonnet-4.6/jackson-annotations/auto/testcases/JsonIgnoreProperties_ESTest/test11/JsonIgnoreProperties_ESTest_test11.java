package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.LinkedHashSet;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test11 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that findIgnoredForDeserialization() returns an empty set when
     * allowSetters=true, regardless of the ignored property names in the Value.
     * When setters are allowed, no properties should be ignored during deserialization.
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        // Obtain the ignored-property set from the default empty Value (will be empty)
        JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.empty();
        Set<String> emptyIgnoredSet = emptyValue.getIgnored();

        // Build a Value with allowSetters=true; this means setters are NOT ignored,
        // so findIgnoredForDeserialization() should return an empty set.
        JsonIgnoreProperties.Value[] valueArray = new JsonIgnoreProperties.Value[6];
        JsonIgnoreProperties.Value valueWithAllowSetters = new JsonIgnoreProperties.Value(
                emptyIgnoredSet,
                /* ignoreUnknown= */ true,
                /* allowGetters=  */ true,
                /* allowSetters=  */ true,
                /* merge=         */ true);
        valueArray[1] = valueWithAllowSetters;

        Set<String> ignoredForDeserialization = valueArray[1].findIgnoredForDeserialization();

        assertTrue(ignoredForDeserialization.isEmpty());
    }
}
