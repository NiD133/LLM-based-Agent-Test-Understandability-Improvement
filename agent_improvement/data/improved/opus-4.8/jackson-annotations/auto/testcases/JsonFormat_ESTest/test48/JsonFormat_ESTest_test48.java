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
public class JsonFormat_ESTest_test48 extends JsonFormat_ESTest_scaffolding {

    /**
     * Two JsonFormat.Value instances built with the same pattern, shape, features,
     * leniency and radix are NOT equal when their locale and time zone differ.
     * One Value is created from a Locale/TimeZone pair, the other from
     * locale/time-zone strings. The test also confirms the basic accessors return
     * the values supplied at construction time.
     */
    @Test(timeout = 4000)
    public void valuesWithDifferentLocaleAndTimeZoneAreNotEqual() throws Throwable {
        String pattern = "FALSE";
        JsonFormat.Shape shape = JsonFormat.Shape.SCALAR;
        JsonFormat.Features noFeatures = JsonFormat.Features.empty();
        Boolean lenient = Boolean.TRUE;
        int radix = 10;

        // Value built from a concrete Locale and TimeZone.
        JsonFormat.Value valueFromObjects = new JsonFormat.Value(
                pattern, shape, Locale.FRENCH, TimeZone.getTimeZone("FALSE"),
                noFeatures, lenient, radix);

        // Value built from locale and time-zone identifiers given as strings.
        JsonFormat.Value valueFromStrings = new JsonFormat.Value(
                pattern, shape, "FALSE", "O",
                noFeatures, lenient, radix);

        boolean areEqual = valueFromObjects.equals(valueFromStrings);
        assertFalse(areEqual);

        // Accessors on the string-built value reflect its constructor arguments.
        assertEquals(10, valueFromStrings.getRadix());
        assertEquals(JsonFormat.Shape.SCALAR, valueFromStrings.getShape());
        assertEquals("FALSE", valueFromStrings.getPattern());
        assertEquals("O", valueFromStrings.timeZoneAsString());

        // Accessors on the object-built value reflect its constructor arguments.
        assertTrue(valueFromObjects.hasPattern());
        assertEquals(JsonFormat.Shape.SCALAR, valueFromObjects.getShape());
        assertEquals(10, valueFromObjects.getRadix());
    }
}
