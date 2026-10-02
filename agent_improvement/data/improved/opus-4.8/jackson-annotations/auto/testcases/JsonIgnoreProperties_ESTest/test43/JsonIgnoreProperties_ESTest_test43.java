package com.fasterxml.jackson.annotation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties.Value;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test43 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Building a Value from an empty set of ignored property names should yield
     * the default flag configuration: getters/setters are not allowed, unknown
     * properties are not ignored, and merging stays enabled.
     */
    @Test(timeout = 4000)
    public void forIgnoredProperties_withEmptySet_keepsDefaultFlags() throws Throwable {
        Set<String> noIgnoredProperties = new LinkedHashSet<String>();

        Value value = Value.forIgnoredProperties(noIgnoredProperties);

        assertFalse("setters should be ignored by default", value.getAllowSetters());
        assertFalse("unknown properties should not be ignored by default", value.getIgnoreUnknown());
        assertFalse("getters should be ignored by default", value.getAllowGetters());
        assertTrue("merging should be enabled by default", value.getMerge());
    }
}
