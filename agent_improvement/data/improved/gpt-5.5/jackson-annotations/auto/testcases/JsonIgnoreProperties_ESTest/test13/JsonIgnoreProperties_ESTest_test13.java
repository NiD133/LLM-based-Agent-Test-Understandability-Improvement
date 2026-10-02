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
public class JsonIgnoreProperties_ESTest_test13 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        LinkedHashSet<String> ignoredProperties = new LinkedHashSet<String>();

        JsonIgnoreProperties.Value ignoreProperties = JsonIgnoreProperties.Value.construct(
                ignoredProperties,
                true,
                true,
                false,
                false);

        ignoreProperties.findIgnoredForSerialization();

        assertTrue(ignoreProperties.getAllowGetters());
        assertFalse(ignoreProperties.getMerge());
        assertTrue(ignoreProperties.getIgnoreUnknown());
        assertFalse(ignoreProperties.getAllowSetters());
    }
}
