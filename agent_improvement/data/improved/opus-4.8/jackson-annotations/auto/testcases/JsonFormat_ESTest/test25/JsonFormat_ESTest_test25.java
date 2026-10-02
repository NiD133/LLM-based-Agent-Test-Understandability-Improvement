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
public class JsonFormat_ESTest_test25 extends JsonFormat_ESTest_scaffolding {

    /**
     * Builds a JsonFormat.Value with an explicit pattern, shape, time zone and radix,
     * then verifies that the simple accessors return exactly what was supplied at
     * construction time.
     */
    @Test(timeout = 4000)
    public void constructorStoresPatternShapeTimeZoneAndRadix() throws Throwable {
        String pattern = "GTN#0o7A|nNN5e%^;@";
        JsonFormat.Shape shape = JsonFormat.Shape.NUMBER_FLOAT;
        TimeZone timeZone = TimeZone.getTimeZone("-d`");
        JsonFormat.Features features = JsonFormat.Features.empty();
        // Boolean.valueOf() of any string other than "true" yields Boolean.FALSE.
        Boolean lenient = Boolean.valueOf("com.fasterxml.jackson.annotation.JsonFormat$Features");
        int radix = 1932;

        JsonFormat.Value value = new JsonFormat.Value(
                pattern, shape, (Locale) null, timeZone, features, lenient, radix);

        assertEquals(1932, value.getRadix());
        assertTrue("NUMBER_FLOAT is not the ANY default, so a shape is set", value.hasShape());
        assertNotNull(value.getTimeZone());
        assertEquals("GTN#0o7A|nNN5e%^;@", value.getPattern());
    }
}
