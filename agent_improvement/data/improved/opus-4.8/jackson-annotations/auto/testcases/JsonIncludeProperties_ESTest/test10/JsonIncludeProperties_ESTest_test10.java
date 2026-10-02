package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test10 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * When a Value is merged with another Value that has the same (defined,
     * non-null) included set, withOverrides() computes the intersection and
     * returns a brand-new instance. That new instance must be a distinct
     * object yet still be equal to the original.
     */
    @Test(timeout = 4000)
    public void mergingValueWithItselfReturnsDistinctButEqualValue() throws Throwable {
        // An empty (but non-null) set of included properties.
        LinkedHashSet<String> includedProperties = new LinkedHashSet<String>();
        // Any non-"true" string parses to Boolean.FALSE for the "ordered" flag.
        Boolean ordered = new Boolean("j:w.BxrN!bO}");

        JsonIncludeProperties.Value original =
                new JsonIncludeProperties.Value(includedProperties, ordered);

        JsonIncludeProperties.Value merged = original.withOverrides(original);

        // Merging produces a freshly allocated Value...
        assertNotSame(merged, original);
        // ...whose contents are identical, so it is still equal to the original.
        assertTrue(merged.equals((Object) original));
    }
}
