package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Locale;
import java.util.SimpleTimeZone;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test40 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that when a {@link JsonFormat.Value} is built with an explicit
     * pattern and radix but a {@code null} shape, the value reports the supplied
     * pattern and radix, while {@code hasShape()} is false (a null shape defaults
     * to {@link JsonFormat.Shape#ANY}).
     */
    @Test(timeout = 4000)
    public void test40() throws Throwable {
        // Given: construction inputs for a JsonFormat.Value, with a null shape.
        String pattern = "$6\"RBn+pQ?N*brK";
        JsonFormat.Shape noShape = null;
        Locale locale = Locale.PRC;
        SimpleTimeZone timeZone = new SimpleTimeZone(2893, "&v1N4|W0j)B");
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        Boolean noLenient = null;
        int radix = 1453;

        // When: building the value.
        JsonFormat.Value formatValue = new JsonFormat.Value(
                pattern, noShape, locale, timeZone, emptyFeatures, noLenient, radix);

        // Then: a null shape means "no specific shape", and pattern/radix are preserved.
        assertFalse(formatValue.hasShape());
        assertEquals(1453, formatValue.getRadix());
        assertEquals("$6\"RBn+pQ?N*brK", formatValue.getPattern());
    }
}
