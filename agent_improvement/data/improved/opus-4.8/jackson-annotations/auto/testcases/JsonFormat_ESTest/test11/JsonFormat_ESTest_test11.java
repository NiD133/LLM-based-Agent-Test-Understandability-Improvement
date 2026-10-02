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
public class JsonFormat_ESTest_test11 extends JsonFormat_ESTest_scaffolding {

    /**
     * Builds a JsonFormat.Value with an explicit pattern, NATURAL shape and a
     * non-default radix, then verifies that the accessors reflect those inputs.
     */
    @Test(timeout = 4000)
    public void valueExposesPatternShapeAndCustomRadix() throws Throwable {
        String pattern = ".sT6~u!yfoEV'=Itz ";
        JsonFormat.Shape shape = JsonFormat.Shape.NATURAL;
        Locale locale = Locale.ITALY;
        TimeZone timeZone = TimeZone.getDefault();
        int customRadix = 95;

        JsonFormat.Value value = new JsonFormat.Value(
                pattern, shape, locale, timeZone,
                (JsonFormat.Features) null, (Boolean) null, customRadix);

        assertTrue("radix differs from the default, so it should be reported as non-default",
                value.hasNonDefaultRadix());
        assertTrue("a non-empty pattern was supplied", value.hasPattern());
        assertEquals(customRadix, value.getRadix());
        assertEquals(JsonFormat.Shape.NATURAL, value.getShape());
    }
}
