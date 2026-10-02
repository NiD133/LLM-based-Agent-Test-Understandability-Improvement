package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test05 extends JsonIncludeProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        // Construct a Value with an empty included-property set and ordered=true
        LinkedHashSet<String> emptyIncludedProperties = new LinkedHashSet<String>();
        Boolean ordered = Boolean.valueOf(true);
        JsonIncludeProperties.Value value = new JsonIncludeProperties.Value(emptyIncludedProperties, ordered);

        // equals(null) must return false per the contract of Object.equals
        boolean isEqualToNull = value.equals((Object) null);
        assertFalse(isEqualToNull);
    }
}
