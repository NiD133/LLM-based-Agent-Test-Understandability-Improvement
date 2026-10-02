package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test12 extends JsonFormat_ESTest_scaffolding {

    /**
     * The empty (default) JsonFormat.Value carries the default radix,
     * so it should report no non-default radix setting.
     */
    @Test(timeout = 4000)
    public void emptyValue_reportsNoNonDefaultRadix() throws Throwable {
        JsonFormat.Value emptyValue = JsonFormat.Value.empty();

        boolean hasNonDefaultRadix = emptyValue.hasNonDefaultRadix();

        assertFalse(hasNonDefaultRadix);
    }
}
