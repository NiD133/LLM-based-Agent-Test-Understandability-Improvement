package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test08 extends JsonFormat_ESTest_scaffolding {

    /**
     * A default JsonFormat.Value is never equal to null, and since no radix
     * was specified it should report that it uses the default radix.
     */
    @Test(timeout = 4000)
    public void defaultValue_isNotEqualToNull_andUsesDefaultRadix() throws Throwable {
        JsonFormat.Value defaultValue = new JsonFormat.Value();

        assertFalse("a Value must never equal null", defaultValue.equals((Object) null));
        assertFalse("default Value should not have a non-default radix",
                defaultValue.hasNonDefaultRadix());
    }
}
