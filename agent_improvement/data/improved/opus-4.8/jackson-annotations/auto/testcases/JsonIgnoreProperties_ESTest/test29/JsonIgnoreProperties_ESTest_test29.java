package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test29 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that applying overrides onto the EMPTY/default Value produces a new
     * Value that is equal to (but not the same instance as) the overrides, because
     * the overrides enable merging and carry a non-default ignored-property set.
     */
    @Test(timeout = 4000)
    public void mergingOverridesOntoEmptyValueYieldsEqualButDistinctValue() throws Throwable {
        // Default (EMPTY) Value: from(null) returns the shared EMPTY instance.
        JsonIgnoreProperties.Value emptyValue =
                JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);

        // Override Value: ignores the property "", merge=true, all allow flags false.
        LinkedHashSet<String> ignoredProperties = new LinkedHashSet<String>();
        ignoredProperties.add("");
        JsonIgnoreProperties.Value overrides = JsonIgnoreProperties.Value.construct(
                ignoredProperties,
                /* ignoreUnknown */ false,
                /* allowGetters  */ false,
                /* allowSetters  */ false,
                /* merge         */ true);

        // Merge the overrides onto the empty base.
        JsonIgnoreProperties.Value merged = emptyValue.withOverrides(overrides);

        // The overrides themselves keep allowSetters disabled.
        assertFalse(overrides.getAllowSetters());

        // The empty base differs from the overrides (it ignores no properties).
        assertFalse(emptyValue.equals((Object) overrides));

        // The merged result is a fresh instance, but is logically equal to the overrides.
        assertNotSame(merged, overrides);
        assertTrue(merged.equals((Object) overrides));
    }
}
