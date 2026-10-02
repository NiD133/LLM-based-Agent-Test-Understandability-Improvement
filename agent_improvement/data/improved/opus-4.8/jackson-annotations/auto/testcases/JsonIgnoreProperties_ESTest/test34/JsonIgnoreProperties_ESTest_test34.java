package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test34 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Building a Value from an empty array of ignored property names should
     * leave allowGetters at its default of false (getters are NOT allowed,
     * i.e. they are ignored along with setters).
     */
    @Test(timeout = 4000)
    public void forIgnoredProperties_withNoNames_doesNotAllowGetters() throws Throwable {
        String[] noIgnoredProperties = new String[0];

        JsonIgnoreProperties.Value value =
                JsonIgnoreProperties.Value.forIgnoredProperties(noIgnoredProperties);

        assertFalse(value.getAllowGetters());
    }
}
