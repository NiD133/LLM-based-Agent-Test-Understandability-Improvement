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
     * A Value created with forLeniency(false) sets lenient=FALSE but leaves all other
     * fields (shape, timezone, radix) at their defaults, so hasShape(), isLenient(),
     * hasNonDefaultRadix() are all false and timeZoneAsString() returns null.
     */
    @Test(timeout = 4000)
    public void test_forLeniencyFalse_defaultsForShapeTimezoneAndRadix() throws Throwable {
        // Arrange: build a Value that only configures leniency to false
        JsonFormat.Value nonLenientValue = JsonFormat.Value.forLeniency(false);

        // Act: retrieve the timezone string representation
        String timezoneString = nonLenientValue.timeZoneAsString();

        // Assert: shape is unset (ANY), leniency flag is false, radix is default, timezone is absent
        assertFalse(nonLenientValue.hasShape());
        assertFalse(nonLenientValue.isLenient());
        assertFalse(nonLenientValue.hasNonDefaultRadix());
        assertNull(timezoneString);
    }
}
