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
public class JsonFormat_ESTest_test13 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that JsonFormat.Value correctly stores and exposes shape, leniency,
     * radix, and pattern when constructed with explicit non-null values.
     *
     * Key behaviors checked:
     * - hasShape() returns true when shape is not Shape.ANY
     * - hasLenient() returns true when lenient is non-null (even if Boolean.FALSE)
     * - getRadix() and getPattern() return the values provided at construction
     */
    @Test(timeout = 4000)
    public void test13() throws Throwable {
        JsonFormat.Shape shape = JsonFormat.Shape.NUMBER_FLOAT;
        // Invalid timezone ID "-d`" resolves to GMT; presence of a non-null TimeZone is what matters here
        TimeZone timezone = TimeZone.getTimeZone("-d`");
        JsonFormat.Features features = JsonFormat.Features.empty();
        // Explicitly set lenient to FALSE (non-null), so hasLenient() must return true
        Boolean lenient = Boolean.FALSE;

        JsonFormat.Value value = new JsonFormat.Value(
                "GTN#0o7A|nNN5e%^;@", shape, (Locale) null, timezone, features, lenient, 1932);

        boolean hasLenient = value.hasLenient();

        // Shape is NUMBER_FLOAT, not the default ANY, so hasShape() must be true
        assertTrue(value.hasShape());
        // lenient was set to Boolean.FALSE (not null), so hasLenient() must be true
        assertTrue(hasLenient);
        assertEquals(1932, value.getRadix());
        assertEquals("GTN#0o7A|nNN5e%^;@", value.getPattern());
    }
}
