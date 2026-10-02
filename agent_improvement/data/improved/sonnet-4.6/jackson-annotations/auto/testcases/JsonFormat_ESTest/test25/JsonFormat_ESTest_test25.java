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
public class JsonFormat_ESTest_test25 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that JsonFormat.Value correctly stores and exposes all constructor
     * arguments: pattern, shape, timezone, and radix, via its getter methods.
     *
     * Boolean.valueOf of any non-"true" string evaluates to false, which is used
     * here as the lenient parameter.
     */
    @Test(timeout = 4000)
    public void test25() throws Throwable {
        JsonFormat.Shape floatShape = JsonFormat.Shape.NUMBER_FLOAT;
        TimeZone invalidTimeZone = TimeZone.getTimeZone("-d`");
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        // Boolean.valueOf with a non-"true" string always returns false
        Boolean lenient = Boolean.valueOf("com.fasterxml.jackson.annotation.JsonFormat$Features");

        JsonFormat.Value formatValue = new JsonFormat.Value(
                "GTN#0o7A|nNN5e%^;@", floatShape, (Locale) null,
                invalidTimeZone, emptyFeatures, lenient, 1932);

        TimeZone retrievedTimeZone = formatValue.getTimeZone();

        assertEquals(1932, formatValue.getRadix());
        assertTrue(formatValue.hasShape());
        assertNotNull(retrievedTimeZone);
        assertEquals("GTN#0o7A|nNN5e%^;@", formatValue.getPattern());
    }
}
