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
     * Verifies that {@link JsonFormat.Value#withOverrides} keeps the override's
     * non-default settings: when a base value is overridden by another value whose
     * pattern, shape, locale, leniency and radix are all set, the result is equal
     * to the override.
     */
    @Test(timeout = 4000)
    public void withOverrides_resultEqualsFullyDefinedOverride() throws Throwable {
        JsonFormat.Features noFeatures = JsonFormat.Features.empty();

        // Base value: shares the same pattern/shape/leniency/radix as the override,
        // but supplies its locale and time zone as JDK objects.
        JsonFormat.Value baseValue = new JsonFormat.Value(
                "FALSE",                       // pattern
                JsonFormat.Shape.SCALAR,       // shape
                Locale.FRENCH,                 // locale
                TimeZone.getTimeZone("FALSE"), // time zone
                noFeatures,                    // features
                Boolean.TRUE,                  // lenient
                10);                           // radix

        // Override value: same pattern/shape/leniency/radix, but its locale and
        // time zone are given as strings.
        JsonFormat.Value overrideValue = new JsonFormat.Value(
                "FALSE",                 // pattern
                JsonFormat.Shape.SCALAR, // shape
                "FALSE",                 // locale string
                "O",                     // time zone string
                noFeatures,              // features
                Boolean.TRUE,            // lenient
                10);                     // radix

        JsonFormat.Value mergedValue = baseValue.withOverrides(overrideValue);

        // The merged value adopts every defined setting of the override.
        assertTrue(mergedValue.equals(overrideValue));
        assertEquals(JsonFormat.Shape.SCALAR, mergedValue.getShape());
        assertEquals("FALSE", mergedValue.getPattern());

        // The base value is unchanged by the merge.
        assertTrue(baseValue.hasPattern());
        assertTrue(baseValue.hasShape());
        assertEquals(10, baseValue.getRadix());
    }
}
