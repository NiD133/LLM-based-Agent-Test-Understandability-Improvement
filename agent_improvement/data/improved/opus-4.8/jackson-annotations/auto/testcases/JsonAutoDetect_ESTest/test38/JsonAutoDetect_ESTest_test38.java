package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test38 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * The DEFAULT Value is configured so that setters of any access modifier
     * are auto-detected, so its setter visibility should be ANY.
     */
    @Test(timeout = 4000)
    public void defaultValueExposesAnySetterVisibility() throws Throwable {
        JsonAutoDetect.Value defaultValue = JsonAutoDetect.Value.DEFAULT;

        JsonAutoDetect.Visibility setterVisibility = defaultValue.getSetterVisibility();

        assertEquals(JsonAutoDetect.Visibility.ANY, setterVisibility);
    }
}
