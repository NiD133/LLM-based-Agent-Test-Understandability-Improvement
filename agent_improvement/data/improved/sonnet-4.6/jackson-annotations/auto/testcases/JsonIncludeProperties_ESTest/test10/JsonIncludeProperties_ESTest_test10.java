package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test10 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * Verifies that calling withOverrides() on a Value with itself returns a new
     * (distinct) instance that is nonetheless equal to the original.
     *
     * When both the base Value and the override Value have an empty included-property
     * set, the intersection is also empty, so the resulting Value holds the same
     * logical state — but withOverrides() always builds a fresh object rather than
     * returning 'this'.
     */
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        // An empty set means "include no properties" (as opposed to null, which means "include all")
        LinkedHashSet<String> emptyIncludedProperties = new LinkedHashSet<String>();
        // Boolean(String) returns false for any string that isn't "true" (case-insensitive)
        Boolean ordered = new Boolean("j:w.BxrN!bO}");

        JsonIncludeProperties.Value original = new JsonIncludeProperties.Value(emptyIncludedProperties, ordered);

        // Merging a Value with itself: intersection of empty ∩ empty = empty, same ordered flag
        JsonIncludeProperties.Value merged = original.withOverrides(original);

        // withOverrides must always produce a new instance (it never returns 'this')
        assertNotSame(merged, original);
        // The merged Value must be logically equal to the original (same empty set, same ordered flag)
        assertTrue(merged.equals((Object) original));
    }
}
