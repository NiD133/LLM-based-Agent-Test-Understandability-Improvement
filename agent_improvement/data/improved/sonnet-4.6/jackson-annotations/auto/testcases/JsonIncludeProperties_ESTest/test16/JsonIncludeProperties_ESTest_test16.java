package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test16 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * Verifies that toString() produces the expected format when the Value is created
     * with an empty set of included properties and a null ordered flag.
     * Expected output: "JsonIncludeProperties.Value(included=[],ordered=null)"
     */
    @Test(timeout = 4000)
    public void test_toString_withEmptyIncludedSetAndNullOrdered_returnsExpectedFormat() throws Throwable {
        LinkedHashSet<String> emptyIncludedProperties = new LinkedHashSet<String>();
        JsonIncludeProperties.Value value = new JsonIncludeProperties.Value(emptyIncludedProperties, (Boolean) null);

        String result = value.toString();

        assertEquals("JsonIncludeProperties.Value(included=[],ordered=null)", result);
    }
}
