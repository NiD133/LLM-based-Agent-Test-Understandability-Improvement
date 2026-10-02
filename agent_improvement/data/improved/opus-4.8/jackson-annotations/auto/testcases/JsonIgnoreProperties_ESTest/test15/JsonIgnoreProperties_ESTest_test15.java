package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test15 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * A non-empty Value (it enables some flags) should survive JDK
     * deserialization unchanged: readResolve() must return an equivalent
     * instance rather than collapsing it to the shared EMPTY singleton,
     * so all of its flag accessors keep the constructed values.
     */
    @Test(timeout = 4000)
    public void readResolveOfNonEmptyValuePreservesFlags() throws Throwable {
        // construct(ignored, ignoreUnknown, allowGetters, allowSetters, merge)
        LinkedHashSet<String> noIgnoredProperties = new LinkedHashSet<String>();
        JsonIgnoreProperties.Value constructedValue = JsonIgnoreProperties.Value.construct(
                noIgnoredProperties,
                /* ignoreUnknown */ true,
                /* allowGetters  */ true,
                /* allowSetters  */ false,
                /* merge         */ true);

        JsonIgnoreProperties.Value resolvedValue =
                (JsonIgnoreProperties.Value) constructedValue.readResolve();

        assertTrue(resolvedValue.getIgnoreUnknown());
        assertFalse(resolvedValue.getAllowSetters());
        assertTrue(resolvedValue.getMerge());
        assertTrue(resolvedValue.getAllowGetters());
    }
}
