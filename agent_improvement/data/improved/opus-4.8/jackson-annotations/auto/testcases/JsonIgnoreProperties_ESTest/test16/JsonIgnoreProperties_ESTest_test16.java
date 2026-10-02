package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test16 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that calling withoutMerge() on a Value whose merge flag is already
     * disabled is idempotent: the second call leaves every flag untouched and the
     * resulting Value still reports merge=false along with all other flags as false.
     */
    @Test(timeout = 4000)
    public void withoutMergeIsIdempotentAndPreservesAllFlags() throws Throwable {
        // Start from a Value with no ignored properties and merge enabled,
        // every other flag disabled.
        LinkedHashSet<String> noIgnoredProperties = new LinkedHashSet<String>();
        JsonIgnoreProperties.Value mergeEnabledValue =
                JsonIgnoreProperties.Value.construct(noIgnoredProperties, false, false, false, true);

        // First call disables merge; the second call should be a no-op.
        JsonIgnoreProperties.Value mergeDisabledValue = mergeEnabledValue.withoutMerge();
        JsonIgnoreProperties.Value mergeDisabledAgain = mergeDisabledValue.withoutMerge();

        // The original value keeps its (default) ignoreUnknown=false.
        assertFalse(mergeEnabledValue.getIgnoreUnknown());

        // All flags on the twice-without-merge value remain false.
        assertFalse(mergeDisabledAgain.getAllowGetters());
        assertFalse(mergeDisabledAgain.getMerge());
        assertFalse(mergeDisabledAgain.getAllowSetters());
        assertFalse(mergeDisabledAgain.getIgnoreUnknown());
    }
}
