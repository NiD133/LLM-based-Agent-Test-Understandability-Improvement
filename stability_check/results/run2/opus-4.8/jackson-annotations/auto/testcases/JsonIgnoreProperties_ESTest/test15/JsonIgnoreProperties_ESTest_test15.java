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
     * A non-empty Value (it differs from EMPTY because ignoreUnknown is true) should
     * survive JDK deserialization: readResolve() must return an equivalent instance
     * that preserves every flag it was constructed with.
     */
    @Test(timeout = 4000)
    public void test15() throws Throwable {
        LinkedHashSet<String> ignoredProperties = new LinkedHashSet<String>();

        // construct(ignored, ignoreUnknown, allowGetters, allowSetters, merge)
        JsonIgnoreProperties.Value original = JsonIgnoreProperties.Value.construct(
                ignoredProperties, true, true, false, true);

        JsonIgnoreProperties.Value resolved =
                (JsonIgnoreProperties.Value) original.readResolve();

        assertTrue(resolved.getIgnoreUnknown());
        assertFalse(resolved.getAllowSetters());
        assertTrue(resolved.getMerge());
        assertTrue(resolved.getAllowGetters());
    }
}
