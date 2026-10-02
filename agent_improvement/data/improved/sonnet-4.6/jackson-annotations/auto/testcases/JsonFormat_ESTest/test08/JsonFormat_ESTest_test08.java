package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test08 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_defaultValue_equalsNull_returnsFalse_andRadixIsDefault() throws Throwable {
        // A default JsonFormat.Value has no custom settings applied
        JsonFormat.Value defaultValue = new JsonFormat.Value();

        // equals(null) must return false per the Object.equals contract
        assertFalse(defaultValue.equals((Object) null));

        // The default radix is DEFAULT_RADIX (-1), so hasNonDefaultRadix() should be false
        assertFalse(defaultValue.hasNonDefaultRadix());
    }
}
