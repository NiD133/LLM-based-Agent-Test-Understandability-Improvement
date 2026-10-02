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
     * Builds a Value via construct(ignored, ignoreUnknown, allowGetters, allowSetters, merge)
     * with ignoreUnknown=true, allowGetters=true, allowSetters=false, merge=false,
     * then verifies each flag is reported back unchanged by its getter.
     */
    @Test(timeout = 4000)
    public void test13() throws Throwable {
        Set<String> noIgnoredProperties = new LinkedHashSet<String>();

        JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.construct(
                noIgnoredProperties,
                /* ignoreUnknown */ true,
                /* allowGetters  */ true,
                /* allowSetters  */ false,
                /* merge         */ false);

        value.findIgnoredForSerialization();

        assertTrue("ignoreUnknown flag should be preserved", value.getIgnoreUnknown());
        assertTrue("allowGetters flag should be preserved", value.getAllowGetters());
        assertFalse("allowSetters flag should be preserved", value.getAllowSetters());
        assertFalse("merge flag should be preserved", value.getMerge());
    }
}
