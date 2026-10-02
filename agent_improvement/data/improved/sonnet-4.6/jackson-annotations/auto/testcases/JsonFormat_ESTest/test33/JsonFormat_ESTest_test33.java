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
public class JsonFormat_ESTest_test33 extends JsonFormat_ESTest_scaffolding {

    /**
     * Tests that withOverrides() correctly merges two Value instances:
     * - Override properties (shape, lenient, radix) replace base properties
     * - An empty timezone string in the override causes the base timezone to be used
     *   (base has no timezone, so the merged result has no timezone either)
     */
    @Test(timeout = 4000)
    public void test33() throws Throwable {
        // Base value with only a pattern; no shape, no timezone, no lenient, default radix
        JsonFormat.Value baseValue = JsonFormat.Value.forPattern("");

        // Override value with a specific shape, custom radix, explicit leniency=false,
        // and an empty timezone string (which means no effective timezone override)
        JsonFormat.Shape integerShape = JsonFormat.Shape.NUMBER_INT;
        SimpleTimeZone timezoneWithEmptyId = new SimpleTimeZone((-5461), "");
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        Boolean notLenient = Boolean.FALSE;
        int customRadix = -1685;
        JsonFormat.Value overrideValue = new JsonFormat.Value(
                "", integerShape, (Locale) null, "", timezoneWithEmptyId,
                emptyFeatures, notLenient, customRadix);

        // Apply the override onto the base
        JsonFormat.Value mergedValue = baseValue.withOverrides(overrideValue);

        // Override's custom radix is preserved in the merged result
        assertEquals((-1685), mergedValue.getRadix());
        // Override's explicit lenient=false setting is reflected (hasLenient checks for non-null)
        assertTrue(mergedValue.hasLenient());
        // Base value's radix was DEFAULT_RADIX, so hasNonDefaultRadix() is false
        assertFalse(baseValue.hasNonDefaultRadix());
        // Override's NUMBER_INT shape is carried into the merged result (NUMBER_INT != ANY)
        assertTrue(mergedValue.hasShape());
        // Merged result differs from the raw override (base contributed its pattern)
        assertFalse(mergedValue.equals((Object) overrideValue));
        // Override's timezone string is empty, so base timezone (null) is used — no timezone
        assertFalse(mergedValue.hasTimeZone());
        // Override value itself has the NUMBER_INT shape
        assertTrue(overrideValue.hasShape());
    }
}
