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

    /**
     * Builds a Value with ignoreUnknown explicitly set to false and verifies
     * that getIgnoreUnknown() reports false. valueFor() is also invoked to
     * exercise the JacksonAnnotationValue contract; its result is not asserted.
     */
    @Test(timeout = 4000)
    public void ignoreUnknownIsFalseWhenConstructedFalse() throws Throwable {
        Set<String> noIgnoredProperties = new LinkedHashSet<String>();

        // construct(ignored, ignoreUnknown, allowGetters, allowSetters, merge)
        JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.construct(
                noIgnoredProperties, false, false, false, true);

        value.valueFor();

        assertFalse(value.getIgnoreUnknown());
    }
}
