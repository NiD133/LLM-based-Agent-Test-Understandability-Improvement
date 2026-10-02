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
public class JsonFormat_ESTest_test81 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test81() throws Throwable {
        // Create a default JsonFormat.Value with no pattern, no timezone, and default radix (-1)
        JsonFormat.Value defaultValue = new JsonFormat.Value();

        // Derive a new Value that adds a pattern; all other properties are copied from defaultValue
        JsonFormat.Value valueWithPattern = defaultValue.withPattern("fS<5P");

        // The original default value should not have a non-default radix set
        assertFalse(defaultValue.hasNonDefaultRadix());

        // withPattern copies the radix unchanged, so it stays at the default value of -1
        assertEquals((-1), valueWithPattern.getRadix());

        // withPattern does not add a timezone, so hasTimeZone() should remain false
        assertFalse(valueWithPattern.hasTimeZone());

        // The derived value should now report that a pattern has been set
        assertTrue(valueWithPattern.hasPattern());
    }
}
