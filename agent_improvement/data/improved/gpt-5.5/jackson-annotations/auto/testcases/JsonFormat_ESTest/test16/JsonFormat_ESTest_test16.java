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
public class JsonFormat_ESTest_test16 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        final String pattern = "FALSE";
        final JsonFormat.Shape shape = JsonFormat.Shape.SCALAR;
        final String localeString = "FALSE";
        final String timeZoneString = "O";
        final JsonFormat.Features features = JsonFormat.Features.empty();
        final Boolean lenient = Boolean.TRUE;
        final int radix = 10;

        JsonFormat.Value formatValue = new JsonFormat.Value(
                pattern,
                shape,
                localeString,
                timeZoneString,
                features,
                lenient,
                radix);

        boolean hasTimeZone = formatValue.hasTimeZone();

        assertEquals("FALSE", formatValue.getPattern());
        assertEquals(10, formatValue.getRadix());
        assertEquals("O", formatValue.timeZoneAsString());
        assertTrue(formatValue.hasShape());
        assertTrue(hasTimeZone);
    }
}
