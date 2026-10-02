package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test51 extends JsonFormat_ESTest_scaffolding {

    /**
     * Enabling a feature on an empty Value should make getFeature() report it as
     * explicitly enabled (Boolean.TRUE), while leaving the radix at its default (-1).
     */
    @Test(timeout = 4000)
    public void enablingFeatureReportsItAsEnabledAndKeepsDefaultRadix() throws Throwable {
        JsonFormat.Feature caseInsensitiveValues = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_VALUES;

        JsonFormat.Value valueWithFeature = JsonFormat.Value.empty().withFeature(caseInsensitiveValues);

        Boolean featureState = valueWithFeature.getFeature(caseInsensitiveValues);
        assertNotNull(featureState);
        assertTrue(featureState);
        assertEquals(JsonFormat.DEFAULT_RADIX, valueWithFeature.getRadix());
    }
}
