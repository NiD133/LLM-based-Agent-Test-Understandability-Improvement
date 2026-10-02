package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test11 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * When a Value is configured with allowSetters = true, deserialization
     * ignorals are disabled, so findIgnoredForDeserialization() returns an
     * empty set regardless of the configured ignored properties.
     */
    @Test(timeout = 4000)
    public void findIgnoredForDeserialization_withAllowSetters_returnsEmptySet() throws Throwable {
        Set<String> noIgnoredProperties = JsonIgnoreProperties.Value.empty().getIgnored();
        JsonIgnoreProperties.Value value = new JsonIgnoreProperties.Value(
                noIgnoredProperties,
                /* ignoreUnknown */ true,
                /* allowGetters  */ true,
                /* allowSetters  */ true,
                /* merge         */ true);

        Set<String> ignoredForDeserialization = value.findIgnoredForDeserialization();

        assertTrue(ignoredForDeserialization.isEmpty());
    }
}
