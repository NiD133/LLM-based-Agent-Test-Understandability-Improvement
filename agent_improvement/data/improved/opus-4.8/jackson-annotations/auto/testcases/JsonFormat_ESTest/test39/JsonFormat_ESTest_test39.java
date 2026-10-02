package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test39 extends JsonFormat_ESTest_scaffolding {

    /**
     * Merging two {@code null} format values yields {@code null}:
     * with no base and no overrides there is nothing to combine.
     */
    @Test(timeout = 4000)
    public void mergeOfTwoNullValuesReturnsNull() throws Throwable {
        JsonFormat.Value merged = JsonFormat.Value.merge((JsonFormat.Value) null, (JsonFormat.Value) null);

        assertNull(merged);
    }
}
