package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.assertSame;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test31 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Merging a base Value with a {@code null} override should leave the base
     * unchanged and return the very same instance.
     */
    @Test(timeout = 4000)
    public void mergeWithNullOverrideReturnsBaseInstance() throws Throwable {
        JsonIgnoreProperties.Value base = JsonIgnoreProperties.Value.empty();

        JsonIgnoreProperties.Value merged = JsonIgnoreProperties.Value.merge(base, (JsonIgnoreProperties.Value) null);

        assertSame(base, merged);
    }
}
