package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test09 extends JsonIncludeProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        // Empty set that will hold the included property names
        LinkedHashSet<String> includedProperties = new LinkedHashSet<String>();

        // Boolean(String) returns false for any string other than "true" (case-insensitive),
        // so this evaluates to Boolean.FALSE
        Boolean ordered = new Boolean("j:w.BxrN!bO}");

        JsonIncludeProperties.Value value = new JsonIncludeProperties.Value(includedProperties, ordered);

        // Removing a Value object from a LinkedHashSet<String> always returns false:
        // the set is empty and Value is not a String element
        boolean removed = includedProperties.remove(value);
        assertFalse(removed);
    }
}
