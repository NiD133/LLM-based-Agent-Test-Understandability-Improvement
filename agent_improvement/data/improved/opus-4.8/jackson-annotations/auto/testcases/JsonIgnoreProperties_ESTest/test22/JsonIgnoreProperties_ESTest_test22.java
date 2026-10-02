package com.fasterxml.jackson.annotation;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test22 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * A Value built from a null annotation falls back to the EMPTY default,
     * where getters are NOT allowed. Calling withoutAllowGetters() on such a
     * value should keep allowGetters disabled.
     */
    @Test(timeout = 4000)
    public void withoutAllowGetters_onEmptyValue_keepsGettersDisabled() throws Throwable {
        JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);

        JsonIgnoreProperties.Value result = emptyValue.withoutAllowGetters();

        assertFalse(result.getAllowGetters());
    }
}
