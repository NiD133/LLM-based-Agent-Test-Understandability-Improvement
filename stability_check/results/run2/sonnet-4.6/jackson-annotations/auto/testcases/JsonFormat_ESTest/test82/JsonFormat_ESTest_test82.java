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
public class JsonFormat_ESTest_test82 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that a JsonFormat.Value created with leniency explicitly disabled
     * has no shape, is not lenient, has no custom radix, and has no timezone set.
     */
    @Test(timeout = 4000)
    public void test82() throws Throwable {
        JsonFormat.Value formatWithLeniencyDisabled = JsonFormat.Value.forLeniency(false);

        String timezoneString = formatWithLeniencyDisabled.timeZoneAsString();

        assertFalse(formatWithLeniencyDisabled.hasShape());
        assertFalse(formatWithLeniencyDisabled.isLenient());
        assertFalse(formatWithLeniencyDisabled.hasNonDefaultRadix());
        assertNull(timezoneString);
    }
}
