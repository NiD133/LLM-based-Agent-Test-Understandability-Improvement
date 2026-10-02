package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test35 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Merging two null Values should yield null, as documented on
     * {@link JsonIgnoreProperties.Value#merge}: if both base and overrides
     * are null, the result is also null.
     */
    @Test(timeout = 4000)
    public void mergeOfTwoNullValuesReturnsNull() throws Throwable {
        JsonIgnoreProperties.Value mergedValue =
                JsonIgnoreProperties.Value.merge((JsonIgnoreProperties.Value) null,
                                                 (JsonIgnoreProperties.Value) null);

        assertNull(mergedValue);
    }
}
