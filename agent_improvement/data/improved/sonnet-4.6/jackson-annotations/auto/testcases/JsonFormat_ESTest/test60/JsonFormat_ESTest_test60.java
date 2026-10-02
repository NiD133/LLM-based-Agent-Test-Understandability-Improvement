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
public class JsonFormat_ESTest_test60 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that withOverrides() produces a Value equal to the override when the override
     * supplies values for every field (pattern, shape, locale, timezone, features, lenient, radix).
     * Also confirms that the original base Value still reports its own properties correctly.
     */
    @Test(timeout = 4000)
    public void test60() throws Throwable {
        // Shared configuration used by both base and override
        JsonFormat.Shape scalarShape = JsonFormat.Shape.SCALAR;
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        Boolean lenient = Boolean.TRUE;
        int radix = 10;

        // Base Value built with actual Locale / TimeZone objects
        JsonFormat.Value baseValue = new JsonFormat.Value(
                "FALSE", scalarShape, Locale.FRENCH, TimeZone.getTimeZone("FALSE"),
                emptyFeatures, lenient, radix);

        // Override Value built with locale and timezone as raw strings
        JsonFormat.Value overrideValue = new JsonFormat.Value(
                "FALSE", scalarShape, "FALSE", "O",
                emptyFeatures, lenient, radix);

        // Applying the override should yield a value identical to the override
        JsonFormat.Value mergedValue = baseValue.withOverrides(overrideValue);
        assertTrue(mergedValue.equals(overrideValue));

        // The base value still reflects its own pattern, shape, and radix
        assertTrue(baseValue.hasPattern());
        assertTrue(baseValue.hasShape());
        assertEquals(10, baseValue.getRadix());

        // The merged value carries the override's pattern and shape
        assertEquals("FALSE", mergedValue.getPattern());
        assertEquals(JsonFormat.Shape.SCALAR, mergedValue.getShape());
    }
}
