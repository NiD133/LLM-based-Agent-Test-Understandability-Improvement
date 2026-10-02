package com.fasterxml.jackson.annotation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Locale;
import java.util.TimeZone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test26 extends JsonFormat_ESTest_scaffolding {

    /**
     * Builds a JsonFormat.Value via the constructor that takes an explicit pattern,
     * shape, locale, time zone and radix, then verifies that the corresponding
     * accessors report the values that were supplied.
     */
    @Test(timeout = 4000)
    public void valueRetainsPatternShapeAndRadix() throws Throwable {
        String pattern = ".sT6~u!yfoEV'=Itz ";
        JsonFormat.Shape shape = JsonFormat.Shape.OBJECT;
        Locale locale = Locale.ITALY;
        TimeZone timeZone = TimeZone.getDefault();
        int radix = 95;

        JsonFormat.Value value = new JsonFormat.Value(
                pattern, shape, locale, timeZone,
                (JsonFormat.Features) null, (Boolean) null, radix);

        // Reading the time zone as a string should not affect the stored state.
        value.timeZoneAsString();

        assertTrue("a non-empty pattern was supplied", value.hasPattern());
        assertEquals(95, value.getRadix());
        assertTrue("a concrete (non-ANY) shape was supplied", value.hasShape());
    }
}
