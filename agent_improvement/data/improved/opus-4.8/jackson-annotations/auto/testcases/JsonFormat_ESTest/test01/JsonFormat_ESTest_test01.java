package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test01 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that the {@link JsonFormat.Value} constructor stores the
     * pattern, shape, time-zone and radix arguments as given, and that the
     * corresponding accessors return those exact values.
     */
    @Test(timeout = 4000)
    public void constructorStoresPatternShapeTimeZoneAndRadix() throws Throwable {
        String pattern = "FALSE";
        JsonFormat.Shape shape = JsonFormat.Shape.OBJECT;
        String localeString = "JsonFormat.Value(pattern=,shape=ANY,lenient=null,locale=null,timezone=null,features=EMPTY,radix=-1639)";
        String timeZoneId = "8e.";
        JsonFormat.Features features = null;
        Boolean lenient = null;
        int radix = -1639;

        JsonFormat.Value formatValue = new JsonFormat.Value(
                pattern, shape, localeString, timeZoneId, features, lenient, radix);

        assertEquals("8e.", formatValue.timeZoneAsString());
        assertEquals((-1639), formatValue.getRadix());
        assertEquals("FALSE", formatValue.getPattern());
        assertEquals(JsonFormat.Shape.OBJECT, formatValue.getShape());
    }
}
