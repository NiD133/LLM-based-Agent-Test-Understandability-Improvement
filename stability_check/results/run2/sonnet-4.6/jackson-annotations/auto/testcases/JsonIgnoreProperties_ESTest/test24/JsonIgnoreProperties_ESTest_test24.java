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
public class JsonIgnoreProperties_ESTest_test24 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that withAllowGetters() returns the same instance when allowGetters is
     * already true, and that other properties (merge, allowSetters, ignoreUnknown) retain
     * their originally constructed values.
     */
    @Test(timeout = 4000)
    public void test24() throws Throwable {
        // Get the empty default Value to retrieve an empty set of ignored properties
        JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.empty();
        Set<String> emptyIgnoredSet = emptyValue.findIgnoredForSerialization();

        // Construct a Value with allowGetters=true already set, plus ignoreUnknown=true,
        // allowSetters=false, and merge=false
        JsonIgnoreProperties.Value valueWithAllowGetters = JsonIgnoreProperties.Value.construct(
                emptyIgnoredSet,
                /* ignoreUnknown */ true,
                /* allowGetters  */ true,
                /* allowSetters  */ false,
                /* merge         */ false
        );

        // Calling withAllowGetters() when allowGetters is already true should return the
        // exact same instance (no new object created)
        JsonIgnoreProperties.Value afterWithAllowGetters = valueWithAllowGetters.withAllowGetters();

        assertSame("withAllowGetters() should be a no-op and return the same instance when allowGetters is already true",
                afterWithAllowGetters, valueWithAllowGetters);

        // The remaining properties must be unchanged from construction
        assertFalse("merge should remain false as originally constructed", afterWithAllowGetters.getMerge());
        assertFalse("allowSetters should remain false as originally constructed", afterWithAllowGetters.getAllowSetters());
        assertTrue("ignoreUnknown should remain true as originally constructed", afterWithAllowGetters.getIgnoreUnknown());
    }
}
