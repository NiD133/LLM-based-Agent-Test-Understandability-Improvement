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
public class JsonIgnoreProperties_ESTest_test08 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that withoutMerge() returns a new Value with merge=false while leaving
     * all other flags (ignoreUnknown, allowGetters, allowSetters) unchanged at false,
     * and that the resulting value is not equal to the original (which had merge=true).
     */
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        // Construct a Value with no ignored properties and merge=true (all other flags false)
        LinkedHashSet<String> noIgnoredProperties = new LinkedHashSet<String>();
        JsonIgnoreProperties.Value valueWithMerge = JsonIgnoreProperties.Value.construct(
                noIgnoredProperties, false, false, false, true);

        // Derive a new Value with merge disabled
        JsonIgnoreProperties.Value valueWithoutMerge = valueWithMerge.withoutMerge();

        // The contains call has no side effects (empty set), but exercises the equals/hashCode path
        LinkedHashSet<Object> emptyObjectSet = new LinkedHashSet<Object>();
        emptyObjectSet.contains(valueWithoutMerge);

        // Both values should have ignoreUnknown=false (unchanged by withoutMerge)
        assertFalse(valueWithMerge.getIgnoreUnknown());
        assertFalse(valueWithoutMerge.getIgnoreUnknown());

        // withoutMerge() must produce a value with merge=false and the other flags still false
        assertFalse(valueWithoutMerge.getMerge());
        assertFalse(valueWithoutMerge.getAllowSetters());
        assertFalse(valueWithoutMerge.getAllowGetters());

        // The two values differ only in the merge flag, so they must not be equal
        assertFalse(valueWithMerge.equals((Object) valueWithoutMerge));
    }
}
