package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test40 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that Value.from(null) returns the EMPTY sentinel, which has merge=true by default.
     * Merge=true means that when combining override settings with base settings, the values are
     * unioned rather than replaced outright.
     */
    @Test(timeout = 4000)
    public void test_fromNullAnnotation_returnsEmptyValueWithMergeEnabled() throws Throwable {
        // Passing null is documented to return the EMPTY instance (since 2.9)
        JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);

        // The EMPTY instance always has merge=true so that override layering works by default
        boolean mergeEnabled = emptyValue.getMerge();
        assertTrue(mergeEnabled);
    }
}
