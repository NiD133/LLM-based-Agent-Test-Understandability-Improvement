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
public class JsonIgnoreProperties_ESTest_test02 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that calling withIgnored() with an empty array on the default empty Value
     * returns the exact same instance (no new object is created).
     *
     * Value.from(null) returns the EMPTY singleton. Passing an empty String array to
     * withIgnored() results in no change to the ignored-properties set, so the method
     * should return 'this' rather than constructing a new Value.
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        // Value.from(null) returns the shared EMPTY singleton
        JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);

        // Calling withIgnored with an empty array should be a no-op and return the same instance
        String[] noProperties = new String[0];
        JsonIgnoreProperties.Value resultValue = emptyValue.withIgnored(noProperties);

        assertSame(resultValue, emptyValue);
    }
}
