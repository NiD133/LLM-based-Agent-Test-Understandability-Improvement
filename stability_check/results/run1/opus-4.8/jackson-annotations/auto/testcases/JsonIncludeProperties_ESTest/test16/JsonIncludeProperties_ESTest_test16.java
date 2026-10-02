package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test16 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * Verifies that toString() renders an empty include set as "[]" and a
     * null "ordered" flag as "null".
     */
    @Test(timeout = 4000)
    public void toStringWithEmptyIncludedAndNullOrdered() throws Throwable {
        LinkedHashSet<String> emptyIncluded = new LinkedHashSet<String>();
        JsonIncludeProperties.Value value =
                new JsonIncludeProperties.Value(emptyIncluded, (Boolean) null);

        String rendered = value.toString();

        assertEquals("JsonIncludeProperties.Value(included=[],ordered=null)", rendered);
    }
}
