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

    /**
     * Verifies that two JsonFormat.Value instances with identical pattern, shape, features,
     * lenient, and radix settings are NOT equal when their timezone representations differ:
     * one holds a resolved TimeZone object (with null _timezoneStr) while the other stores
     * the timezone as a raw string. The equals() method compares _timezoneStr fields, so
     * the instance with a null _timezoneStr never matches one with a non-null _timezoneStr.
     */
    @Test(timeout = 4000)
    public void test48() throws Throwable {
        JsonFormat.Shape scalarShape = JsonFormat.Shape.SCALAR;
        Locale frenchLocale = Locale.FRENCH;
        // TimeZone with unrecognized ID "FALSE" — resolves to GMT
        TimeZone resolvedTimeZone = TimeZone.getTimeZone("FALSE");
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        Boolean lenientTrue = Boolean.TRUE;
        int radix = 10;

        // Value constructed with a resolved TimeZone object: internally stores _timezone=resolvedTimeZone
        // and leaves _timezoneStr=null (as per the Locale+TimeZone constructor).
        JsonFormat.Value valueWithResolvedTimeZone = new JsonFormat.Value(
                "FALSE", scalarShape, frenchLocale, resolvedTimeZone, emptyFeatures, lenientTrue, radix);

        // Value constructed with string-based locale and timezone: internally stores _timezoneStr="O"
        // (since "O" is non-empty and not DEFAULT_TIMEZONE) and locale parsed from "FALSE".
        JsonFormat.Value valueWithStringTimeZone = new JsonFormat.Value(
                "FALSE", scalarShape, "FALSE", "O", emptyFeatures, lenientTrue, radix);

        // equals() compares _timezoneStr fields: valueWithResolvedTimeZone has null, valueWithStringTimeZone has "O"
        boolean areEqual = valueWithResolvedTimeZone.equals(valueWithStringTimeZone);

        // Verify properties of valueWithStringTimeZone
        assertEquals(10, valueWithStringTimeZone.getRadix());
        assertEquals(JsonFormat.Shape.SCALAR, valueWithStringTimeZone.getShape());
        assertEquals("FALSE", valueWithStringTimeZone.getPattern());
        assertEquals("O", valueWithStringTimeZone.timeZoneAsString());

        // The two values must not be equal because their timezone representations differ
        assertFalse(areEqual);

        // Verify properties of valueWithResolvedTimeZone
        assertTrue(valueWithResolvedTimeZone.hasPattern());
        assertEquals(JsonFormat.Shape.SCALAR, valueWithResolvedTimeZone.getShape());
        assertEquals(10, valueWithResolvedTimeZone.getRadix());
    }
}
