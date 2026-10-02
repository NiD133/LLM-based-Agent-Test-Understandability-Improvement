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
     * Value.from(null) returns the shared EMPTY instance, which has merging
     * enabled by default. Verify that getMerge() reports true for it.
     */
    @Test(timeout = 4000)
    public void fromNullAnnotationHasMergeEnabled() throws Throwable {
        JsonIgnoreProperties.Value emptyValue =
                JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);

        boolean mergeEnabled = emptyValue.getMerge();

        assertTrue(mergeEnabled);
    }
}
