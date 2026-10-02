package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test13 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that a Value constructed with allowGetters=true and ignoreUnknown=true
     * has the correct flags, and that findIgnoredForSerialization() can be called
     * without error (returns empty because allowGetters suppresses serialization ignoral).
     */
    @Test(timeout = 4000)
    public void test_findIgnoredForSerialization_whenAllowGettersIsTrue() throws Throwable {
        Set<String> noIgnoredProperties = new LinkedHashSet<String>();
        JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.construct(
                noIgnoredProperties,
                /* ignoreUnknown */ true,
                /* allowGetters  */ true,
                /* allowSetters  */ false,
                /* merge         */ false);

        // allowGetters=true means getters are NOT ignored during serialization
        value.findIgnoredForSerialization();

        assertTrue(value.getAllowGetters());
        assertFalse(value.getMerge());
        assertTrue(value.getIgnoreUnknown());
        assertFalse(value.getAllowSetters());
    }
}
