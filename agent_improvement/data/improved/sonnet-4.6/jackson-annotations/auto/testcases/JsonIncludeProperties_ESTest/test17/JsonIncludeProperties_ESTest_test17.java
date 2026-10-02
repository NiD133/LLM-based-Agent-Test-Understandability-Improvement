package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test17 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * Verifies that getOrdered() returns the Boolean.FALSE value that was
     * supplied at construction time, confirming the field is stored and
     * returned without modification.
     */
    @Test(timeout = 4000)
    public void getOrdered_returnsFalse_whenValueConstructedWithFalseOrdered() throws Throwable {
        LinkedHashSet<String> emptyIncludedProperties = new LinkedHashSet<String>();
        Boolean ordered = Boolean.FALSE;
        JsonIncludeProperties.Value value = new JsonIncludeProperties.Value(emptyIncludedProperties, ordered);

        Boolean result = value.getOrdered();

        assertFalse(result);
    }
}
