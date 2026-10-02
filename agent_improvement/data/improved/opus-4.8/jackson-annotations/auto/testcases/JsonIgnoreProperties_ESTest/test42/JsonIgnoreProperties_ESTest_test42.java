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
     * Constructs a Value with no ignored properties, ignoreUnknown=false,
     * allowGetters=false, allowSetters=false and merge=true, then verifies
     * that the "ignoreUnknown" and "allowGetters" flags are reported as false.
     */
    @Test(timeout = 4000)
    public void allowGettersAndIgnoreUnknownAreFalseWhenConstructedFalse() throws Throwable {
        LinkedHashSet<String> noIgnoredProperties = new LinkedHashSet<String>();

        JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.construct(
                noIgnoredProperties,
                /* ignoreUnknown */ false,
                /* allowGetters  */ false,
                /* allowSetters  */ false,
                /* merge         */ true);

        assertFalse("ignoreUnknown should be false", value.getIgnoreUnknown());
        assertFalse("allowGetters should be false", value.getAllowGetters());
    }
}
