package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test36 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Value.from(null) yields the EMPTY value, whose merge flag defaults to true.
     * Merging that value with itself must preserve the merge flag.
     */
    @Test(timeout = 4000)
    public void mergeOfEmptyValueWithItselfKeepsMergeEnabled() throws Throwable {
        JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);

        JsonIgnoreProperties.Value mergedValue = JsonIgnoreProperties.Value.merge(emptyValue, emptyValue);

        assertTrue(mergedValue.getMerge());
    }
}
