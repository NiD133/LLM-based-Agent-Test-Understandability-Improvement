package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test07 extends JsonIncludeProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        // Build a Value with an empty included-property set and ordered=false
        LinkedHashSet<String> emptyIncluded = new LinkedHashSet<String>();
        Boolean notOrdered = Boolean.FALSE;
        JsonIncludeProperties.Value value = new JsonIncludeProperties.Value(emptyIncluded, notOrdered);

        // equals() must return false when the other object is not a JsonIncludeProperties.Value
        Object unrelatedObject = new Object();
        boolean result = value.equals(unrelatedObject);
        assertFalse(result);
    }
}
