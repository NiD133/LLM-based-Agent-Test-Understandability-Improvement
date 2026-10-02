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
     * Builds a {@link JsonFormat.Value} via the full constructor and verifies that
     * the explicitly supplied pattern, shape, leniency and radix are reported back
     * correctly by the corresponding accessors.
     */
    @Test(timeout = 4000)
    public void test13() throws Throwable {
        String pattern = "GTN#0o7A|nNN5e%^;@";
        JsonFormat.Shape shape = JsonFormat.Shape.NUMBER_FLOAT;
        Locale locale = null;
        TimeZone timeZone = TimeZone.getTimeZone("-d`");
        JsonFormat.Features features = JsonFormat.Features.empty();
        Boolean lenient = Boolean.FALSE;
        int radix = 1932;

        JsonFormat.Value value = new JsonFormat.Value(
                pattern, shape, locale, timeZone, features, lenient, radix);

        // Leniency was explicitly set (to FALSE), so it counts as "has a value".
        assertTrue(value.hasLenient());
        // A concrete (non-ANY) shape was provided.
        assertTrue(value.hasShape());
        assertEquals(1932, value.getRadix());
        assertEquals("GTN#0o7A|nNN5e%^;@", value.getPattern());
    }
}
