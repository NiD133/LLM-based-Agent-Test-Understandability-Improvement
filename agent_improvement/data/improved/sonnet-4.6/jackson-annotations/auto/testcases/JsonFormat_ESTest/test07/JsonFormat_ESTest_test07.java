package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Locale;
import java.util.TimeZone;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test07 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        JsonFormat.Shape scalarShape = JsonFormat.Shape.SCALAR;
        Locale frenchLocale = Locale.FRENCH;
        // "FALSE" is not a valid timezone ID; TimeZone.getTimeZone falls back to GMT
        TimeZone unknownTimeZone = TimeZone.getTimeZone("FALSE");
        JsonFormat.Features noFeatures = JsonFormat.Features.empty();
        Boolean lenient = Boolean.TRUE;

        JsonFormat.Value formatValue = new JsonFormat.Value(
                "FALSE", scalarShape, frenchLocale, unknownTimeZone, noFeatures, lenient, 10);

        // Value.equals() must return false when compared against an incompatible type
        boolean equalsTimeZone = formatValue.equals(unknownTimeZone);
        assertFalse(equalsTimeZone);

        // Radix was explicitly set to 10 in the constructor
        assertEquals(10, formatValue.getRadix());

        // Shape is SCALAR (not ANY), so a shape has been set
        assertTrue(formatValue.hasShape());

        // Pattern is "FALSE" (non-empty string), so a pattern has been set
        assertTrue(formatValue.hasPattern());
    }
}
