package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test09 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that a default JsonFormat.Value instance satisfies two properties:
     * 1. It is equal to itself (reflexive equality).
     * 2. Its radix defaults to JsonFormat.DEFAULT_RADIX (-1), meaning no custom
     *    numeric base has been specified for Number-to-String serialization.
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        JsonFormat.Value defaultValue = new JsonFormat.Value();

        boolean isEqualToItself = defaultValue.equals(defaultValue);
        assertTrue(isEqualToItself);

        assertEquals(JsonFormat.DEFAULT_RADIX, defaultValue.getRadix());
    }
}
