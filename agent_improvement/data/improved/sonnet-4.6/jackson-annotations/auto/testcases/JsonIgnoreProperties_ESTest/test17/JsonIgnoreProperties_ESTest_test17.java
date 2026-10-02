package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test17 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that Value.from(null) returns the default EMPTY value (merge=true),
     * and that calling withMerge() on an already-merging value preserves merge=true.
     */
    @Test(timeout = 4000)
    public void test_withMerge_onDefaultValue_returnsMergeEnabled() throws Throwable {
        // Value.from(null) returns the EMPTY default, which has merge=true by default
        JsonIgnoreProperties.Value defaultValue = JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);

        // withMerge() is a no-op here since merge is already true, but should still return merge=true
        JsonIgnoreProperties.Value valueWithMerge = defaultValue.withMerge();

        assertTrue(valueWithMerge.getMerge());
    }
}
