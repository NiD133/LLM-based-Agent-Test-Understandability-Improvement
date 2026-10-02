package com.fasterxml.jackson.annotation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Locale;
import java.util.TimeZone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test07 extends JsonFormat_ESTest_scaffolding {

    /**
     * Builds a fully-populated JsonFormat.Value and verifies that:
     * <ul>
     *   <li>the configured radix (10) is reported back unchanged,</li>
     *   <li>a non-ANY shape is detected as "has shape",</li>
     *   <li>a non-empty pattern is detected as "has pattern", and</li>
     *   <li>the Value is not equal to an unrelated object (a TimeZone).</li>
     * </ul>
     */
    @Test(timeout = 4000)
    public void testAccessorsAndUnequalToUnrelatedObject() throws Throwable {
        String pattern = "FALSE";
        JsonFormat.Shape shape = JsonFormat.Shape.SCALAR;
        Locale locale = Locale.FRENCH;
        TimeZone timeZone = TimeZone.getTimeZone("FALSE");
        JsonFormat.Features features = JsonFormat.Features.empty();
        Boolean lenient = Boolean.TRUE;
        int radix = 10;

        JsonFormat.Value value = new JsonFormat.Value(
                pattern, shape, locale, timeZone, features, lenient, radix);

        // A JsonFormat.Value should never be considered equal to an unrelated type.
        boolean equalsTimeZone = value.equals(timeZone);

        assertEquals(10, value.getRadix());
        assertTrue(value.hasShape());
        assertFalse(equalsTimeZone);
        assertTrue(value.hasPattern());
    }
}
