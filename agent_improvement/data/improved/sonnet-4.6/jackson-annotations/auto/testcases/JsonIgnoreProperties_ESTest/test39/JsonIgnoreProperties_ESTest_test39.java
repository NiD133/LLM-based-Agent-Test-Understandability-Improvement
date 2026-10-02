package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test39 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test39() throws Throwable {
        Set<String> noIgnoredProperties = new LinkedHashSet<String>();
        JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.construct(
                noIgnoredProperties,
                /* ignoreUnknown= */ false,
                /* allowGetters=  */ false,
                /* allowSetters=  */ false,
                /* merge=         */ true);

        value.valueFor();

        assertFalse("ignoreUnknown was set to false, so getIgnoreUnknown() should return false",
                value.getIgnoreUnknown());
    }
}
