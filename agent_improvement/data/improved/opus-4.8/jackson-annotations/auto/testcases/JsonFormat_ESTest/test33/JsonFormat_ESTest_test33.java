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
public class JsonFormat_ESTest_test33 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies how {@link JsonFormat.Value#withOverrides} merges a base value
     * (created from a pattern only) with an override value that explicitly sets
     * shape, time zone, leniency and a non-default radix.
     */
    @Test(timeout = 4000)
    public void mergingPatternBaseWithFullOverrideAppliesOverrideFields() throws Throwable {
        // Base value: only a (blank) pattern is set, everything else is left at defaults.
        JsonFormat.Value base = JsonFormat.Value.forPattern("");

        // Override value: sets shape, time zone, lenient flag and a custom radix.
        JsonFormat.Value override = new JsonFormat.Value(
                "",                          // pattern
                JsonFormat.Shape.NUMBER_INT, // shape
                (Locale) null,               // locale
                "",                          // timezone id
                new SimpleTimeZone(-5461, ""), // explicit TimeZone instance
                JsonFormat.Features.empty(), // features
                Boolean.FALSE,               // lenient
                -1685);                      // radix

        // The base has no explicit radix, so the override never reads from it.
        assertFalse(base.hasNonDefaultRadix());
        // The override itself carries the explicit shape.
        assertTrue(override.hasShape());

        JsonFormat.Value merged = base.withOverrides(override);

        // The override's custom radix wins.
        assertEquals(-1685, merged.getRadix());
        // The override's lenient setting (FALSE) is carried over, so leniency is set.
        assertTrue(merged.hasLenient());
        // The override's explicit shape is carried over.
        assertTrue(merged.hasShape());
        // The override supplied a blank timezone id, so the merged value has no time zone.
        assertFalse(merged.hasTimeZone());
        // Merged value differs from the override (it kept the base's blank pattern handling).
        assertFalse(merged.equals(override));
    }
}
