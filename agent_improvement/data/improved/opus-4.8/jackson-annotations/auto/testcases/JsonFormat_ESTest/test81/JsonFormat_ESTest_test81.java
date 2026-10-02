package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test81 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonFormat.Value#withPattern(String)} returns a new value that
     * carries the supplied pattern, while leaving the unrelated radix and time-zone settings
     * at their defaults (no radix override, no time zone).
     */
    @Test(timeout = 4000)
    public void withPatternSetsPatternAndKeepsRadixAndTimeZoneDefault() throws Throwable {
        JsonFormat.Value defaultValue = new JsonFormat.Value();
        JsonFormat.Value valueWithPattern = defaultValue.withPattern("fS<5P");

        // The original default value never had a radix override.
        assertFalse(defaultValue.hasNonDefaultRadix());

        // The derived value carries the pattern but inherits the default radix and no time zone.
        assertEquals(JsonFormat.DEFAULT_RADIX, valueWithPattern.getRadix());
        assertFalse(valueWithPattern.hasTimeZone());
        assertTrue(valueWithPattern.hasPattern());
    }
}
