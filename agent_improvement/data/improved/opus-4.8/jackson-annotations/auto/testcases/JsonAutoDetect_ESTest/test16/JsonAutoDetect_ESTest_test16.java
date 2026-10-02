package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test16 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Merging two null Values should yield null: with a null base, merge simply
     * returns the (also null) overrides argument.
     */
    @Test(timeout = 4000)
    public void mergingTwoNullValuesReturnsNull() throws Throwable {
        JsonAutoDetect.Value merged = JsonAutoDetect.Value.merge(null, null);

        assertNull(merged);
    }
}
