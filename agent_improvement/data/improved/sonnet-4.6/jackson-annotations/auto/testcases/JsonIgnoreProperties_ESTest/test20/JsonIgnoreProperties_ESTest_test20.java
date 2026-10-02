package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.LinkedHashSet;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test20 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that withoutAllowSetters() produces a distinct Value instance
     * with allowSetters disabled, while leaving other flags (allowGetters,
     * ignoreUnknown, merge) unchanged.
     */
    @Test(timeout = 4000)
    public void test20() throws Throwable {
        // Construct a Value where only allowSetters is true; all other flags are false
        JsonIgnoreProperties.Value valueWithAllowSetters =
                JsonIgnoreProperties.Value.construct((Set<String>) null,
                        /*ignoreUnknown=*/ false,
                        /*allowGetters=*/ false,
                        /*allowSetters=*/ true,
                        /*merge=*/        false);

        // withoutAllowSetters() must return a new instance with allowSetters disabled
        JsonIgnoreProperties.Value valueWithoutAllowSetters = valueWithAllowSetters.withoutAllowSetters();

        // The two instances must not be the same object reference
        assertNotSame(valueWithoutAllowSetters, valueWithAllowSetters);

        // The original instance should still have allowGetters=false (unchanged)
        assertFalse(valueWithAllowSetters.getAllowGetters());

        // The new instance must preserve merge=false and ignoreUnknown=false from the original
        assertFalse(valueWithoutAllowSetters.getMerge());
        assertFalse(valueWithoutAllowSetters.getIgnoreUnknown());

        // The original instance should also still have ignoreUnknown=false (unchanged)
        assertFalse(valueWithAllowSetters.getIgnoreUnknown());

        // The core postcondition: the new instance must have allowSetters=false
        assertFalse(valueWithoutAllowSetters.getAllowSetters());
    }
}
