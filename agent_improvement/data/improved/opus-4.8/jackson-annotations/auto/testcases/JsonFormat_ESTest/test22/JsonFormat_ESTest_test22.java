package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test22 extends JsonFormat_ESTest_scaffolding {

    /**
     * A default-constructed JsonFormat.Value carries no explicit shape and no
     * custom radix, so both hasShape() and hasNonDefaultRadix() must report false.
     */
    @Test(timeout = 4000)
    public void defaultValueHasNoShapeAndNoCustomRadix() throws Throwable {
        JsonFormat.Value defaultValue = new JsonFormat.Value();

        assertFalse("default Value should have no explicit shape", defaultValue.hasShape());
        assertFalse("default Value should use the default radix", defaultValue.hasNonDefaultRadix());
    }
}
