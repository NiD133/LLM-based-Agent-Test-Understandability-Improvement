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

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        JsonFormat.Shape configuredShape = JsonFormat.Shape.NATURAL;
        Locale configuredLocale = Locale.ITALY;
        TimeZone configuredTimeZone = TimeZone.getDefault();

        JsonFormat.Value formatValue = new JsonFormat.Value(
                ".sT6~u!yfoEV'=Itz ",
                configuredShape,
                configuredLocale,
                configuredTimeZone,
                (JsonFormat.Features) null,
                (Boolean) null,
                95);

        boolean hasCustomRadix = formatValue.hasNonDefaultRadix();

        assertTrue(formatValue.hasPattern());
        assertEquals(95, formatValue.getRadix());
        assertEquals(JsonFormat.Shape.NATURAL, formatValue.getShape());
        assertTrue(hasCustomRadix);
    }
}
