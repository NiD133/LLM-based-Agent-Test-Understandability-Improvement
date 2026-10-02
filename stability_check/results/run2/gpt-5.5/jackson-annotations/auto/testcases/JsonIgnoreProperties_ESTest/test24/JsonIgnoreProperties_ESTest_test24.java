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
public class JsonIgnoreProperties_ESTest_test24 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test24() throws Throwable {
        JsonIgnoreProperties.Value emptySettings = JsonIgnoreProperties.Value.empty();
        Set<String> ignoredDuringSerialization = emptySettings.findIgnoredForSerialization();

        JsonIgnoreProperties.Value settingsWithAllowedGetters =
                JsonIgnoreProperties.Value.construct(ignoredDuringSerialization, true, true, false, false);
        JsonIgnoreProperties.Value result = settingsWithAllowedGetters.withAllowGetters();

        assertFalse(result.getMerge());
        assertFalse(result.getAllowSetters());
        assertSame(result, settingsWithAllowedGetters);
        assertTrue(result.getIgnoreUnknown());
    }
}
