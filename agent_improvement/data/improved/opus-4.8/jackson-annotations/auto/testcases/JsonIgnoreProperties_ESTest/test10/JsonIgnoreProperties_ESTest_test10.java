package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test10 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonIgnoreProperties.Value#construct} stores each
     * boolean flag exactly as supplied and exposes it through the matching getter.
     */
    @Test(timeout = 4000)
    public void constructRetainsAllBooleanFlags() throws Throwable {
        LinkedHashSet<String> ignoredProperties = new LinkedHashSet<String>();

        // construct(ignored, ignoreUnknown, allowGetters, allowSetters, merge)
        JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.construct(
                ignoredProperties, true, true, false, true);

        // Removing a Value from a Set<String> is a no-op; preserved from the
        // original generated test to keep behaviour identical.
        ignoredProperties.remove(value);

        assertTrue("ignoreUnknown flag should be retained", value.getIgnoreUnknown());
        assertTrue("merge flag should be retained", value.getMerge());
        assertTrue("allowGetters flag should be retained", value.getAllowGetters());
        assertFalse("allowSetters flag should be retained", value.getAllowSetters());
    }
}
