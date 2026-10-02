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

    // Verifies that toString() produces the expected format when included set is empty and ordered flag is null
    @Test(timeout = 4000)
    public void test16() throws Throwable {
        LinkedHashSet<String> emptyIncludedProperties = new LinkedHashSet<String>();
        JsonIncludeProperties.Value valueWithNoIncludedAndNullOrdered =
                new JsonIncludeProperties.Value(emptyIncludedProperties, (Boolean) null);

        String stringRepresentation = valueWithNoIncludedAndNullOrdered.toString();

        assertEquals("JsonIncludeProperties.Value(included=[],ordered=null)", stringRepresentation);
    }
}
