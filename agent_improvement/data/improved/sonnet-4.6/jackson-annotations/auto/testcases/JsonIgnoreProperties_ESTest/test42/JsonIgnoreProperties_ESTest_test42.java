package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test42 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that a Value constructed with allowGetters=false and ignoreUnknown=false
     * correctly reports both flags as false via their respective accessors.
     */
    @Test(timeout = 4000)
    public void test42_constructValueWithNoGettersAndNoIgnoreUnknown_returnsFalseForBoth() throws Throwable {
        LinkedHashSet<String> noIgnoredProperties = new LinkedHashSet<String>();
        JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.construct(
                noIgnoredProperties,
                /* ignoreUnknown */ false,
                /* allowGetters  */ false,
                /* allowSetters  */ false,
                /* merge         */ true);

        boolean allowGetters = value.getAllowGetters();

        assertFalse("getAllowGetters() should be false when constructed with allowGetters=false",
                value.getIgnoreUnknown());
        assertFalse("getAllowGetters() should return false",
                allowGetters);
    }
}
