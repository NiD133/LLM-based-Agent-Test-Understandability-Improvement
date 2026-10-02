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
public class JsonFormat_ESTest_test04 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // A default Value has no pattern, shape=ANY, no timezone, and default radix
        JsonFormat.Value defaultValue = new JsonFormat.Value();

        // A Value created with only a pattern should differ from the default (which has no pattern)
        JsonFormat.Value patternOnlyValue = JsonFormat.Value.forPattern("*h,2D`=nR6aV]Mg'.");

        boolean isEqualToDefault = patternOnlyValue.equals(defaultValue);

        // forPattern sets only the pattern; shape remains ANY (no specific shape configured)
        assertFalse(patternOnlyValue.hasShape());
        // The pattern-only value differs from the default value (which has an empty pattern)
        assertFalse(isEqualToDefault);
        // forPattern does not set a radix, so it remains the default
        assertFalse(patternOnlyValue.hasNonDefaultRadix());
        // forPattern does not set a timezone
        assertFalse(patternOnlyValue.hasTimeZone());
    }
}
