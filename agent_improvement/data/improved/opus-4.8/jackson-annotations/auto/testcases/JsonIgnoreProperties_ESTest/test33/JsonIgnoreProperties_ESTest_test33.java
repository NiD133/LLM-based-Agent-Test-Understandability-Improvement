package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test33 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * forIgnoreUnknown(false) should produce a Value whose
     * ignoreUnknown flag is disabled.
     */
    @Test(timeout = 4000)
    public void forIgnoreUnknownFalse_disablesIgnoreUnknownFlag() throws Throwable {
        JsonIgnoreProperties.Value valueWithIgnoreUnknownDisabled =
                JsonIgnoreProperties.Value.forIgnoreUnknown(false);

        assertFalse(valueWithIgnoreUnknownDisabled.getIgnoreUnknown());
    }
}
