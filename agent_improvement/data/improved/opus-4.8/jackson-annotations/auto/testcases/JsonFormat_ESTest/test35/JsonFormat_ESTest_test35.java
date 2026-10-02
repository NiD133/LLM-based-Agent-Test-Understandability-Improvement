package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test35 extends JsonFormat_ESTest_scaffolding {

    /**
     * Merging the empty Value with itself should yield a Value that still
     * carries no pattern, since neither side defines one.
     */
    @Test(timeout = 4000)
    public void mergingEmptyValueWithItselfHasNoPattern() throws Throwable {
        JsonFormat.Value emptyValue = JsonFormat.Value.empty();

        JsonFormat.Value merged = JsonFormat.Value.merge(emptyValue, emptyValue);

        assertFalse(merged.hasPattern());
    }
}
