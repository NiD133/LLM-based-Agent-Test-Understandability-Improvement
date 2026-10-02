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
public class JsonIgnoreProperties_ESTest_test15 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Tests that readResolve() preserves a non-default Value instance.
     *
     * When a Value has non-default settings (ignoreUnknown=true, allowGetters=true,
     * allowSetters=false, merge=true) with an empty ignored-properties set,
     * readResolve() should return the same instance (not the EMPTY singleton),
     * and all property values should be unchanged after deserialization resolution.
     */
    @Test(timeout = 4000)
    public void test15() throws Throwable {
        // Construct a Value with an empty ignored-properties set and specific flags:
        //   ignoreUnknown=true, allowGetters=true, allowSetters=false, merge=true
        Set<String> emptyIgnoredProperties = new LinkedHashSet<String>();
        JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.construct(
                emptyIgnoredProperties,
                /* ignoreUnknown */ true,
                /* allowGetters  */ true,
                /* allowSetters  */ false,
                /* merge         */ true);

        // readResolve() is called during Java deserialization; it should return 'this'
        // because the value is not equivalent to the EMPTY singleton
        JsonIgnoreProperties.Value resolvedValue = (JsonIgnoreProperties.Value) value.readResolve();

        // Verify that all property flags survive the readResolve() call unchanged
        assertTrue("ignoreUnknown should be true after readResolve", resolvedValue.getIgnoreUnknown());
        assertFalse("allowSetters should be false after readResolve", resolvedValue.getAllowSetters());
        assertTrue("merge should be true after readResolve", resolvedValue.getMerge());
        assertTrue("allowGetters should be true after readResolve", resolvedValue.getAllowGetters());
    }
}
