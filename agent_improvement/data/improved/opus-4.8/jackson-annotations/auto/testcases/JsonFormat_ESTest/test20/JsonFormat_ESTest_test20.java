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
public class JsonFormat_ESTest_test20 extends JsonFormat_ESTest_scaffolding {

    /**
     * Builds a fully-specified {@link JsonFormat.Value} via its constructor and verifies
     * that the pattern, shape, and radix it was created with are reported back correctly.
     */
    @Test(timeout = 4000)
    public void valueRetainsPatternShapeAndRadixFromConstructor() throws Throwable {
        String pattern = "FALSE";
        JsonFormat.Shape shape = JsonFormat.Shape.SCALAR;
        Locale locale = Locale.FRENCH;
        TimeZone timeZone = TimeZone.getTimeZone("FALSE");
        JsonFormat.Features features = JsonFormat.Features.empty();
        Boolean lenient = Boolean.TRUE;
        int radix = 10;

        JsonFormat.Value formatValue = new JsonFormat.Value(
                pattern, shape, locale, timeZone, features, lenient, radix);

        assertTrue("non-empty pattern should be reported as present", formatValue.hasPattern());
        assertTrue("an explicit (non-ANY) shape should be reported as present", formatValue.hasShape());
        assertEquals("radix should match the constructor argument", 10, formatValue.getRadix());
    }
}
