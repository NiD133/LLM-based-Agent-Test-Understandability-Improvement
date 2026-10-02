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
     * Verifies that a Value created with forLeniency(false) has leniency explicitly
     * disabled while all other properties (shape, radix, timezone) remain unset.
     *
     * Note: isLenient() returns true only when leniency is explicitly TRUE.
     * Setting it to FALSE makes the value non-lenient, but hasLenient() would still
     * return true (indicating the property was explicitly configured).
     */
    @Test(timeout = 4000)
    public void test82() throws Throwable {
        // Create a format Value with leniency explicitly set to false
        JsonFormat.Value nonLenientValue = JsonFormat.Value.forLeniency(false);

        // Retrieve timezone representation — should be null since no timezone was configured
        String timezoneStr = nonLenientValue.timeZoneAsString();

        // Shape was not specified, so hasShape() should return false (shape defaults to ANY)
        assertFalse(nonLenientValue.hasShape());
        // isLenient() returns true only if lenient is Boolean.TRUE; FALSE means not lenient
        assertFalse(nonLenientValue.isLenient());
        // No custom radix was set, so the value uses the default radix
        assertFalse(nonLenientValue.hasNonDefaultRadix());
        // No timezone was provided to forLeniency(), so the string representation is null
        assertNull(timezoneStr);
    }
}
