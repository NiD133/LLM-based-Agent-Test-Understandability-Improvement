package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
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

    @Test(timeout = 4000)
    public void test85() throws Throwable {
        // Build a JsonFormat.Value with SCALAR shape, a non-empty pattern, French locale,
        // an unrecognised timezone ("FALSE"), empty features, lenient=true, and radix=10.
        JsonFormat.Shape scalarShape = JsonFormat.Shape.SCALAR;
        Locale frenchLocale = Locale.FRENCH;
        TimeZone falseTimeZone = TimeZone.getTimeZone("FALSE");
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        Boolean lenient = Boolean.TRUE;
        JsonFormat.Value formatValue = new JsonFormat.Value(
                "FALSE", scalarShape, frenchLocale, falseTimeZone, emptyFeatures, lenient, 10);

        // Attempting to remove the value from an empty map is a no-op and must not throw.
        HashMap<String, List<String>> emptyMap = new HashMap<String, List<String>>();
        emptyMap.remove((Object) formatValue);

        // The constructed value must retain its shape, pattern presence, and radix.
        assertEquals(JsonFormat.Shape.SCALAR, formatValue.getShape());
        assertTrue(formatValue.hasPattern());
        assertEquals(10, formatValue.getRadix());
    }
}
