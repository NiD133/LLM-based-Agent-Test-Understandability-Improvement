package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test01 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Merging a base Value with an override that only flips "ignoreUnknown" on
     * should yield a result equal to the override (since both share the same
     * ignored properties and merge flag), with ignoreUnknown enabled and both
     * getter/setter ignorals still in effect.
     */
    @Test(timeout = 4000)
    public void mergeWithIgnoreUnknownOverrideMatchesOverride() throws Throwable {
        // Base: ignore six (null) property names, leaving all other flags at defaults.
        String[] ignoredProperties = new String[6];
        JsonIgnoreProperties.Value base =
                JsonIgnoreProperties.Value.forIgnoredProperties(ignoredProperties);

        // Override: same base, but with "ignoreUnknown" turned on.
        JsonIgnoreProperties.Value ignoreUnknownOverride = base.withIgnoreUnknown();

        // Merge base settings with the override.
        JsonIgnoreProperties.Value merged =
                JsonIgnoreProperties.Value.merge(base, ignoreUnknownOverride);

        // The merged value is logically equal to the override...
        assertTrue(merged.equals((Object) ignoreUnknownOverride));
        // ...but is a distinct instance.
        assertNotSame(merged, ignoreUnknownOverride);

        // Merged value carries the override's "ignoreUnknown" flag.
        assertTrue(merged.getIgnoreUnknown());
        // Getter/setter ignorals remain in effect (not allowed) on the merged value.
        assertFalse(merged.getAllowGetters());
        assertFalse(merged.getAllowSetters());

        // The base value keeps merge enabled and getters/setters not allowed.
        assertTrue(base.getMerge());
        assertFalse(base.getAllowSetters());
    }
}
