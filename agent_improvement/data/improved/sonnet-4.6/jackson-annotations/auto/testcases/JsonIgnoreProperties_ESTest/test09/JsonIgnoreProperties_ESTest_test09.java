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
public class JsonIgnoreProperties_ESTest_test09 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that withAllowSetters() enables allowSetters while preserving
     * the other flags (ignoreUnknown=false, allowGetters=false, merge=true).
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        // Construct a base Value with no ignored properties, ignoreUnknown=false,
        // allowGetters=false, allowSetters=false, and merge=true
        LinkedHashSet<String> noIgnoredProperties = new LinkedHashSet<String>();
        JsonIgnoreProperties.Value baseValue = JsonIgnoreProperties.Value.construct(
                noIgnoredProperties, false, false, false, true);

        // Apply withAllowSetters() to produce a new Value with allowSetters=true
        LinkedHashSet<Object> objectSet = new LinkedHashSet<Object>();
        JsonIgnoreProperties.Value valueWithAllowSetters = baseValue.withAllowSetters();
        objectSet.contains(valueWithAllowSetters);

        // Confirm that only allowSetters was changed; other flags remain as constructed
        assertFalse(valueWithAllowSetters.getIgnoreUnknown());
        assertFalse(valueWithAllowSetters.getAllowGetters());
        assertTrue(valueWithAllowSetters.getAllowSetters());
        assertTrue(valueWithAllowSetters.getMerge());
    }
}
