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
public class JsonIgnoreProperties_ESTest_test10 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that a Value constructed with ignoreUnknown=true, allowGetters=true,
     * allowSetters=false, and merge=true retains those settings correctly.
     * Also confirms that attempting to remove the Value object from the (String-typed)
     * ignored-properties set has no effect on the Value's state.
     */
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        // An empty set of property names to ignore
        LinkedHashSet<String> ignoredProperties = new LinkedHashSet<String>();

        // Construct a Value with: no ignored fields, ignoreUnknown=true,
        // allowGetters=true, allowSetters=false, merge=true
        JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.construct(
                ignoredProperties,
                /* ignoreUnknown */ true,
                /* allowGetters  */ true,
                /* allowSetters  */ false,
                /* merge         */ true);

        // Removing the Value object from the String-typed set is a no-op
        // (type mismatch: Value is not a String), so the Value is unaffected
        ignoredProperties.remove(value);

        // The constructed Value must reflect exactly the flags passed to construct()
        assertTrue(value.getIgnoreUnknown());
        assertTrue(value.getMerge());
        assertTrue(value.getAllowGetters());
        assertFalse(value.getAllowSetters());
    }
}
