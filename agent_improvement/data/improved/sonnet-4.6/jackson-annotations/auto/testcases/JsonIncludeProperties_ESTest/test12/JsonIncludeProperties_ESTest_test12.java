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
public class JsonIncludeProperties_ESTest_test12 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * Verifies that applying withOverrides(self) produces a new Value instance
     * that is equal to the original but not the same object reference.
     *
     * When a Value is overridden with itself, the intersection of included
     * properties is the same set, so the result should be logically equal
     * but physically distinct (a fresh copy).
     */
    @Test(timeout = 4000)
    public void test_withOverrides_self_returnsEqualButDistinctInstance() throws Throwable {
        LinkedHashSet<String> includedProperties = new LinkedHashSet<String>();
        includedProperties.add("U 6?8|");

        // Create a Value with one included property and no ordering defined (null)
        JsonIncludeProperties.Value baseValue = new JsonIncludeProperties.Value(includedProperties, (Boolean) null);

        // Applying withOverrides with itself computes the intersection of the included
        // sets (same set ∩ same set = same set), producing a new Value instance
        JsonIncludeProperties.Value overriddenValue = baseValue.withOverrides(baseValue);

        // The result must be a different object (new instance was created)
        assertNotSame(overriddenValue, baseValue);

        // The result must be logically equal (same included properties, same ordered flag)
        assertEquals(overriddenValue, baseValue);
    }
}
