package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test03 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * Two Value instances sharing the same (empty) included-properties set but
     * differing in their "ordered" flag must not be considered equal.
     */
    @Test(timeout = 4000)
    public void test_equals_returnsFalse_whenOrderedFlagDiffers() throws Throwable {
        LinkedHashSet<String> emptyIncluded = new LinkedHashSet<String>();

        // Value with ordered = false
        JsonIncludeProperties.Value valueUnordered = new JsonIncludeProperties.Value(emptyIncluded, Boolean.FALSE);

        // Value with ordered = true — same included set, different ordered flag
        JsonIncludeProperties.Value valueOrdered = new JsonIncludeProperties.Value(emptyIncluded, Boolean.TRUE);

        boolean areEqual = valueOrdered.equals(valueUnordered);

        assertFalse("Values with different 'ordered' flags should not be equal", areEqual);
    }
}
