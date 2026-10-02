package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test46 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * The empty Value has no special permissions, so "allow setters" must be
     * disabled by default (ignorals apply to setters as well as getters).
     */
    @Test(timeout = 4000)
    public void emptyValueDoesNotAllowSetters() throws Throwable {
        JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.empty();

        boolean allowSetters = emptyValue.getAllowSetters();

        assertFalse(allowSetters);
    }
}
