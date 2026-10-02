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
public class JsonFormat_ESTest_test11 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that a JsonFormat.Value constructed with a non-default radix (95)
     * correctly reports hasNonDefaultRadix() as true, and that the pattern,
     * shape, and radix values are preserved as supplied.
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        // Arrange: build a Value with a non-empty pattern, NATURAL shape, and radix=95
        JsonFormat.Shape shape = JsonFormat.Shape.NATURAL;
        Locale locale = Locale.ITALY;
        TimeZone timeZone = TimeZone.getDefault();
        int nonDefaultRadix = 95;
        JsonFormat.Value value = new JsonFormat.Value(
                ".sT6~u!yfoEV'=Itz ", shape, locale, timeZone,
                (JsonFormat.Features) null, (Boolean) null, nonDefaultRadix);

        // Act: check whether the radix is recognised as non-default
        boolean isNonDefaultRadix = value.hasNonDefaultRadix();

        // Assert: pattern, shape, and radix are stored correctly;
        //         hasNonDefaultRadix() returns true because 95 != DEFAULT_RADIX (-1)
        assertTrue(value.hasPattern());
        assertEquals(nonDefaultRadix, value.getRadix());
        assertEquals(JsonFormat.Shape.NATURAL, value.getShape());
        assertTrue(isNonDefaultRadix);
    }
}
