package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test19 extends JsonIncludeProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        // An empty set of included property names
        LinkedHashSet<String> emptyIncludedNames = new LinkedHashSet<String>();

        // Create a Value with the empty set and ordered=true
        Boolean ordered = new Boolean("TRUE");
        JsonIncludeProperties.Value includeConfig = new JsonIncludeProperties.Value(emptyIncludedNames, ordered);

        // The empty string set should not contain the Value object itself
        boolean setContainsValueObject = emptyIncludedNames.contains(includeConfig);
        assertFalse(setContainsValueObject);
    }
}
