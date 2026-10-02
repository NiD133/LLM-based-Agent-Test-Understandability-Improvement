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
public class JsonIgnoreProperties_ESTest_test43 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that a Value created from an empty set of ignored property names
     * has the expected defaults: allowSetters=false, ignoreUnknown=false,
     * allowGetters=false, and merge=true (merging is enabled by default).
     */
    @Test(timeout = 4000)
    public void test43() throws Throwable {
        Set<String> emptyIgnoredProperties = new LinkedHashSet<String>();
        JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.forIgnoredProperties(emptyIgnoredProperties);

        // By default, setters for ignored properties are not allowed through
        assertFalse(value.getAllowSetters());
        // By default, unknown properties are not silently ignored during deserialization
        assertFalse(value.getIgnoreUnknown());
        // By default, getters for ignored properties are not allowed through
        assertFalse(value.getAllowGetters());
        // Merging is enabled by default so override settings combine with base settings
        assertTrue(value.getMerge());
    }
}
