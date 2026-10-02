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
public class JsonFormat_ESTest_test07 extends JsonFormat_ESTest_scaffolding {

    private static final String NON_DEFAULT_PATTERN = "FALSE";
    private static final int CUSTOM_RADIX = 10;

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        JsonFormat.Shape scalarShape = JsonFormat.Shape.SCALAR;
        Locale frenchLocale = Locale.FRENCH;
        TimeZone falseTimeZone = TimeZone.getTimeZone("FALSE");
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        Boolean explicitLeniency = Boolean.TRUE;

        JsonFormat.Value scalarValue = new JsonFormat.Value(
                NON_DEFAULT_PATTERN,
                scalarShape,
                frenchLocale,
                falseTimeZone,
                emptyFeatures,
                explicitLeniency,
                CUSTOM_RADIX);

        boolean equalsTimeZone = scalarValue.equals(falseTimeZone);

        assertEquals(CUSTOM_RADIX, scalarValue.getRadix());
        assertTrue(scalarValue.hasShape());
        assertFalse(equalsTimeZone);
        assertTrue(scalarValue.hasPattern());
    }
}
