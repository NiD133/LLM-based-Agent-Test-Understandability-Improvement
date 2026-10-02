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
     * When a non-empty Value (with several flags set) is deserialized via
     * readResolve(), the resolved instance must preserve every configured flag
     * rather than collapsing to the shared EMPTY singleton.
     */
    @Test(timeout = 4000)
    public void readResolvePreservesFlagsForNonEmptyValue() throws Throwable {
        // Build a Value with: no ignored names, ignoreUnknown=true,
        // allowGetters=true, allowSetters=false, merge=true.
        LinkedHashSet<String> noIgnoredProperties = new LinkedHashSet<String>();
        JsonIgnoreProperties.Value configuredValue = JsonIgnoreProperties.Value.construct(
                noIgnoredProperties, true, true, false, true);

        JsonIgnoreProperties.Value resolvedValue =
                (JsonIgnoreProperties.Value) configuredValue.readResolve();

        assertTrue(resolvedValue.getIgnoreUnknown());
        assertFalse(resolvedValue.getAllowSetters());
        assertTrue(resolvedValue.getMerge());
        assertTrue(resolvedValue.getAllowGetters());
    }
}
