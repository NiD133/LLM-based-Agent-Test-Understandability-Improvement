package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import java.util.LinkedHashSet;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test44 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that when a Value is constructed with ignoreUnknown=false,
     * the getIgnoreUnknown() accessor reports false.
     */
    @Test(timeout = 4000)
    public void getIgnoreUnknownReturnsFalseWhenConstructedFalse() throws Throwable {
        Set<String> ignoredProperties = new LinkedHashSet<String>();

        JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.construct(
                ignoredProperties,
                /* ignoreUnknown */ false,
                /* allowGetters  */ false,
                /* allowSetters  */ false,
                /* merge         */ true);

        assertFalse(value.getIgnoreUnknown());
    }
}
