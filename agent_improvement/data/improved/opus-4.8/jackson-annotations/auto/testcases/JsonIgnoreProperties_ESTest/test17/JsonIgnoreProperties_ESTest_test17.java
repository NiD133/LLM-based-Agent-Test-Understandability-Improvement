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
     * A null annotation source yields the EMPTY Value, which already has merge enabled.
     * Calling withMerge() on it should keep merge turned on.
     */
    @Test(timeout = 4000)
    public void withMerge_onEmptyValue_keepsMergeEnabled() throws Throwable {
        JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);

        JsonIgnoreProperties.Value mergedValue = emptyValue.withMerge();

        assertTrue(mergedValue.getMerge());
    }
}
