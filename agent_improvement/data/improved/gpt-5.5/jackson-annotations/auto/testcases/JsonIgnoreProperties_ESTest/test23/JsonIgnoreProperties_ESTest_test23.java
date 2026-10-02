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
public class JsonIgnoreProperties_ESTest_test23 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test23() throws Throwable {
        JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.empty();
        Set<String> ignoredForSerialization = emptyValue.findIgnoredForSerialization();

        JsonIgnoreProperties.Value valueAllowingGetters =
                JsonIgnoreProperties.Value.construct(ignoredForSerialization, true, true, false, false);
        JsonIgnoreProperties.Value valueWithoutAllowedGetters =
                valueAllowingGetters.withoutAllowGetters();

        assertNotSame(valueWithoutAllowedGetters, valueAllowingGetters);

        assertFalse(valueAllowingGetters.getMerge());
        assertFalse(valueAllowingGetters.getAllowSetters());
        assertTrue(valueAllowingGetters.getIgnoreUnknown());

        assertTrue(valueWithoutAllowedGetters.getIgnoreUnknown());
        assertFalse(valueWithoutAllowedGetters.getAllowGetters());
        assertFalse(valueWithoutAllowedGetters.getAllowSetters());
        assertFalse(valueWithoutAllowedGetters.getMerge());
    }
}
