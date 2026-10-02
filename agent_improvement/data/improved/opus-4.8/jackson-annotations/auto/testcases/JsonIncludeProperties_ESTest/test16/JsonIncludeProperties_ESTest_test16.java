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
     * toString() should render an empty set of included properties as "[]"
     * and an undefined (null) ordering flag as "null".
     */
    @Test(timeout = 4000)
    public void toString_withEmptyIncludedAndNullOrdered_describesBoth() throws Throwable {
        LinkedHashSet<String> emptyIncluded = new LinkedHashSet<String>();
        Boolean undefinedOrdered = null;
        JsonIncludeProperties.Value value =
                new JsonIncludeProperties.Value(emptyIncluded, undefinedOrdered);

        String description = value.toString();

        assertEquals("JsonIncludeProperties.Value(included=[],ordered=null)", description);
    }
}
