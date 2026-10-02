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
public class JsonFormat_ESTest_test48 extends JsonFormat_ESTest_scaffolding {

    private static final String PATTERN = "FALSE";
    private static final String TIME_ZONE_ID = "FALSE";
    private static final String EXPLICIT_TIME_ZONE_TEXT = "O";
    private static final int RADIX = 10;

    @Test(timeout = 4000)
    public void test48() throws Throwable {
        JsonFormat.Shape scalarShape = JsonFormat.Shape.SCALAR;
        Locale frenchLocale = Locale.FRENCH;
        TimeZone falseTimeZone = TimeZone.getTimeZone(TIME_ZONE_ID);
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        Boolean lenient = Boolean.TRUE;

        JsonFormat.Value valueWithTimeZoneObject = new JsonFormat.Value(
                PATTERN, scalarShape, frenchLocale, falseTimeZone, emptyFeatures, lenient, RADIX);
        JsonFormat.Value valueWithTimeZoneText = new JsonFormat.Value(
                PATTERN, scalarShape, PATTERN, EXPLICIT_TIME_ZONE_TEXT, emptyFeatures, lenient, RADIX);

        boolean valuesAreEqual = valueWithTimeZoneObject.equals(valueWithTimeZoneText);

        assertEquals(RADIX, valueWithTimeZoneText.getRadix());
        assertEquals(JsonFormat.Shape.SCALAR, valueWithTimeZoneText.getShape());
        assertTrue(valueWithTimeZoneObject.hasPattern());
        assertEquals(PATTERN, valueWithTimeZoneText.getPattern());
        assertEquals(EXPLICIT_TIME_ZONE_TEXT, valueWithTimeZoneText.timeZoneAsString());
        assertFalse(valuesAreEqual);
        assertEquals(JsonFormat.Shape.SCALAR, valueWithTimeZoneObject.getShape());
        assertEquals(RADIX, valueWithTimeZoneObject.getRadix());
    }
}
