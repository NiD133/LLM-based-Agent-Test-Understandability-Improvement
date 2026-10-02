package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.LinkedHashSet;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test41 extends JsonIgnoreProperties_ESTest_scaffolding {

    // Verifies that toString() on a Value with no ignored properties and only merge=true
    // produces the expected formatted string representation.
    @Test(timeout = 4000)
    public void test_toString_withNoIgnoredPropertiesAndMergeEnabled() throws Throwable {
        Set<String> noIgnoredProperties = new LinkedHashSet<String>();
        JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.construct(
                noIgnoredProperties,
                /* ignoreUnknown */ false,
                /* allowGetters  */ false,
                /* allowSetters  */ false,
                /* merge         */ true
        );

        String result = value.toString();

        assertEquals(
                "JsonIgnoreProperties.Value(ignored=[],ignoreUnknown=false,allowGetters=false,allowSetters=false,merge=true)",
                result
        );
    }
}
