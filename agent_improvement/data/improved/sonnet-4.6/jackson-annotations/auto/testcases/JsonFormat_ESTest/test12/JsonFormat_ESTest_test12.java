package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test12 extends JsonFormat_ESTest_scaffolding {

    // An empty Value carries the DEFAULT_RADIX sentinel (-1), so hasNonDefaultRadix() must return false.
    @Test(timeout = 4000)
    public void test_emptyValue_hasNoNonDefaultRadix() throws Throwable {
        JsonFormat.Value emptyValue = JsonFormat.Value.empty();
        boolean hasNonDefaultRadix = emptyValue.hasNonDefaultRadix();
        assertFalse(hasNonDefaultRadix);
    }
}
