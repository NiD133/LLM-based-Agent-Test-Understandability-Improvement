package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test37 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * The default Value uses NON_PRIVATE visibility for single-scalar-argument
     * constructors, so getScalarConstructorVisibility() should report NON_PRIVATE.
     */
    @Test(timeout = 4000)
    public void defaultScalarConstructorVisibilityIsNonPrivate() throws Throwable {
        JsonAutoDetect.Value defaultValue = JsonAutoDetect.Value.defaultVisibility();

        JsonAutoDetect.Visibility scalarConstructorVisibility =
                defaultValue.getScalarConstructorVisibility();

        assertEquals(JsonAutoDetect.Visibility.NON_PRIVATE, scalarConstructorVisibility);
    }
}
