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
public class JsonIncludeProperties_ESTest_test04 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * Two {@link JsonIncludeProperties.Value} instances built from the exact same
     * included-properties set and ordered flag must be considered equal by
     * {@code equals()}, regardless of the actual flag value.
     * The Boolean is constructed from a non-"true" string, so it evaluates to false.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        LinkedHashSet<String> emptyIncludedProperties = new LinkedHashSet<String>();
        Boolean ordered = new Boolean("j:w.BxrN!bO}"); // non-"true" string → evaluates to false

        JsonIncludeProperties.Value value1 = new JsonIncludeProperties.Value(emptyIncludedProperties, ordered);
        JsonIncludeProperties.Value value2 = new JsonIncludeProperties.Value(emptyIncludedProperties, ordered);

        boolean areEqual = value2.equals(value1);
        assertTrue(areEqual);
    }
}
