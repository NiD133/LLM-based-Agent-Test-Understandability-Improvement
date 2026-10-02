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
public class JsonFormat_ESTest_test13 extends JsonFormat_ESTest_scaffolding {

    private static final String PATTERN = "GTN#0o7A|nNN5e%^;@";
    private static final String TIME_ZONE_ID = "-d`";
    private static final String LENIENT_TEXT = "com.fasterxml.jackson.annotation.JsonFormat$Features";
    private static final int RADIX = 1932;

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        JsonFormat.Shape numberFloatShape = JsonFormat.Shape.NUMBER_FLOAT;
        TimeZone timeZone = TimeZone.getTimeZone(TIME_ZONE_ID);
        JsonFormat.Features noFormatFeatures = JsonFormat.Features.empty();
        Boolean lenient = Boolean.valueOf(LENIENT_TEXT);

        JsonFormat.Value formatValue = new JsonFormat.Value(
                PATTERN,
                numberFloatShape,
                (Locale) null,
                timeZone,
                noFormatFeatures,
                lenient,
                RADIX);

        boolean hasExplicitLeniency = formatValue.hasLenient();

        assertTrue(formatValue.hasShape());
        assertTrue(hasExplicitLeniency);
        assertEquals(RADIX, formatValue.getRadix());
        assertEquals(PATTERN, formatValue.getPattern());
    }
}
