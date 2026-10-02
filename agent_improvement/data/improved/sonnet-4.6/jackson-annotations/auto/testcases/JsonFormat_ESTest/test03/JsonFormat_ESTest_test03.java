package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test03 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that applying a non-default locale via withLocale() produces a Value
     * that is not equal to the original default Value, while both retain the default
     * radix (DEFAULT_RADIX = -1) and the locale-specific copy reports no custom radix.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // Arrange: a Value with all defaults (locale = null, radix = DEFAULT_RADIX)
        JsonFormat.Value defaultValue = new JsonFormat.Value();

        // Act: create a copy that overrides the locale to GERMAN
        JsonFormat.Value germanLocaleValue = defaultValue.withLocale(Locale.GERMAN);

        // The two instances should differ because their locales differ
        boolean areEqual = germanLocaleValue.equals(defaultValue);

        // Assert: the locale change is reflected in inequality
        assertFalse(areEqual);

        // Assert: the locale-specific copy still carries the default radix (no custom radix set)
        assertFalse(germanLocaleValue.hasNonDefaultRadix());

        // Assert: the original default Value also reports DEFAULT_RADIX (-1)
        assertEquals((-1), defaultValue.getRadix());
    }
}
