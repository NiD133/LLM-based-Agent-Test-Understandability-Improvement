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
public class JsonFormat_ESTest_test26 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test26() throws Throwable {
        final String nonEmptyPattern = ".sT6~u!yfoEV'=Itz ";
        final JsonFormat.Shape objectShape = JsonFormat.Shape.OBJECT;
        final Locale italianLocale = Locale.ITALY;
        final TimeZone defaultTimeZone = TimeZone.getDefault();
        final int explicitRadix = 95;

        JsonFormat.Value formatValue = new JsonFormat.Value(
                nonEmptyPattern,
                objectShape,
                italianLocale,
                defaultTimeZone,
                (JsonFormat.Features) null,
                (Boolean) null,
                explicitRadix);

        formatValue.timeZoneAsString();

        assertTrue(formatValue.hasPattern());
        assertEquals(95, formatValue.getRadix());
        assertTrue(formatValue.hasShape());
    }
}
