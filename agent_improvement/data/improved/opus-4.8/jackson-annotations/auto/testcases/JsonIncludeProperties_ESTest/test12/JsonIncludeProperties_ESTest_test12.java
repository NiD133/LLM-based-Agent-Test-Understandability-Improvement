package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test12 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * Overriding a Value with itself (a Value that has a defined, non-null
     * "included" set) should return a brand-new Value instance whose included
     * set is the intersection of the two (identical) sets. The result is a
     * different object but is considered equal to the original.
     */
    @Test(timeout = 4000)
    public void withOverridesUsingSelfReturnsEqualButDistinctValue() throws Throwable {
        LinkedHashSet<String> includedProperties = new LinkedHashSet<String>();
        includedProperties.add("U 6?8|");

        JsonIncludeProperties.Value original =
                new JsonIncludeProperties.Value(includedProperties, (Boolean) null);

        JsonIncludeProperties.Value merged = original.withOverrides(original);

        assertNotSame(merged, original);
        assertTrue(merged.equals((Object) original));
    }
}
