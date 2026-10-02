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
public class JsonIgnoreProperties_ESTest_test36 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that merging two default (EMPTY) Value instances produces a Value
     * whose merge flag is true. Value.from(null) returns the EMPTY singleton,
     * which has merge=true by design; merging it with itself should preserve that.
     */
    @Test(timeout = 4000)
    public void test_mergeOfTwoDefaultValues_hasMergeEnabled() throws Throwable {
        // Value.from(null) returns the EMPTY default Value, which has merge=true
        JsonIgnoreProperties.Value defaultValue = JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);

        // Merging a default Value with itself should still yield a Value with merge=true
        JsonIgnoreProperties.Value mergedValue = JsonIgnoreProperties.Value.merge(defaultValue, defaultValue);

        assertTrue(mergedValue.getMerge());
    }
}
