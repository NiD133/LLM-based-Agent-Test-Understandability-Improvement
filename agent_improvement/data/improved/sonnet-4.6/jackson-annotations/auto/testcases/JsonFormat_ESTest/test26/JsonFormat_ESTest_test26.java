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
public class JsonFormat_ESTest_test26 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test26() throws Throwable {
        // Construct a JsonFormat.Value with an explicit pattern, OBJECT shape,
        // Italy locale, the JVM default timezone, no features, no lenient flag,
        // and a custom radix of 95.
        String pattern = ".sT6~u!yfoEV'=Itz ";
        JsonFormat.Shape shape = JsonFormat.Shape.OBJECT;
        Locale locale = Locale.ITALY;
        TimeZone timeZone = TimeZone.getDefault();
        int radix = 95;

        JsonFormat.Value formatValue = new JsonFormat.Value(
                pattern, shape, locale, timeZone,
                (JsonFormat.Features) null, (Boolean) null, radix);

        // Retrieve the timezone identifier string (exercises the timezone string accessor)
        formatValue.timeZoneAsString();

        // A non-empty pattern means hasPattern() must return true
        assertTrue(formatValue.hasPattern());
        // The custom radix must be preserved exactly
        assertEquals(radix, formatValue.getRadix());
        // OBJECT is not Shape.ANY, so hasShape() must return true
        assertTrue(formatValue.hasShape());
    }
}
