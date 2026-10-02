package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test85 extends JsonFormat_ESTest_scaffolding {

    /**
     * Builds a fully-specified JsonFormat.Value and verifies that the
     * pattern, shape and radix it was constructed with are reported back
     * correctly by its accessors. Removing the Value from an empty HashMap
     * is a no-op and must not disturb the Value's state.
     */
    @Test(timeout = 4000)
    public void test85() throws Throwable {
        String pattern = "FALSE";
        JsonFormat.Shape shape = JsonFormat.Shape.SCALAR;
        Locale locale = Locale.FRENCH;
        TimeZone timeZone = TimeZone.getTimeZone("FALSE");
        JsonFormat.Features features = JsonFormat.Features.empty();
        Boolean lenient = Boolean.TRUE;
        int radix = 10;

        JsonFormat.Value formatValue =
                new JsonFormat.Value(pattern, shape, locale, timeZone, features, lenient, radix);

        // Removing the value from an empty map does nothing.
        HashMap<String, List<String>> emptyMap = new HashMap<String, List<String>>();
        emptyMap.remove((Object) formatValue);

        assertEquals(JsonFormat.Shape.SCALAR, formatValue.getShape());
        assertTrue(formatValue.hasPattern());
        assertEquals(10, formatValue.getRadix());
    }
}
