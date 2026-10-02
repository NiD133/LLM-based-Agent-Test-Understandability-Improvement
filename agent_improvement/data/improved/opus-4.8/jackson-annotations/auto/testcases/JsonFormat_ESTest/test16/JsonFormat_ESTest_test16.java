package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test16 extends JsonFormat_ESTest_scaffolding {

    /**
     * Builds a JsonFormat.Value via the full constructor and verifies that the
     * accessors echo back the constructor arguments: the pattern, the radix,
     * the timezone string, the presence of an explicit (non-ANY) shape, and the
     * presence of a timezone.
     */
    @Test(timeout = 4000)
    public void test16() throws Throwable {
        String pattern = "FALSE";
        JsonFormat.Shape shape = JsonFormat.Shape.SCALAR;
        String localeStr = "FALSE";
        String timeZoneStr = "O";
        JsonFormat.Features features = JsonFormat.Features.empty();
        Boolean lenient = Boolean.TRUE;
        int radix = 10;

        JsonFormat.Value value = new JsonFormat.Value(
                pattern, shape, localeStr, timeZoneStr, features, lenient, radix);

        assertEquals("FALSE", value.getPattern());
        assertEquals(10, value.getRadix());
        assertEquals("O", value.timeZoneAsString());
        assertTrue("SCALAR is an explicit (non-ANY) shape", value.hasShape());
        assertTrue("a non-empty timezone string was supplied", value.hasTimeZone());
    }
}
