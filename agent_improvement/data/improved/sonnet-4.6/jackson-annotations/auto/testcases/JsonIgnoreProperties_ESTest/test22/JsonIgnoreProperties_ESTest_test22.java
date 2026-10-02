package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test22 extends JsonIgnoreProperties_ESTest_scaffolding {

    // Value.from(null) returns the EMPTY default whose allowGetters is false;
    // withoutAllowGetters() on an already-false value is a no-op and stays false.
    @Test(timeout = 4000)
    public void test_withoutAllowGetters_onDefaultValue_returnsFalse() throws Throwable {
        JsonIgnoreProperties.Value defaultValue = JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);
        JsonIgnoreProperties.Value valueWithGettersDisabled = defaultValue.withoutAllowGetters();
        assertFalse(valueWithGettersDisabled.getAllowGetters());
    }
}
