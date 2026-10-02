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
public class JsonFormat_ESTest_test60 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test60() throws Throwable {
        final JsonFormat.Shape scalarShape = JsonFormat.Shape.SCALAR;
        final Locale frenchLocale = Locale.FRENCH;
        final TimeZone falseTimeZone = TimeZone.getTimeZone("FALSE");
        final JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        final Boolean lenient = Boolean.TRUE;

        final JsonFormat.Value baseValue = new JsonFormat.Value(
                "FALSE", scalarShape, frenchLocale, falseTimeZone, emptyFeatures, lenient, 10);
        final JsonFormat.Value overrideValue = new JsonFormat.Value(
                "FALSE", scalarShape, "FALSE", "O", emptyFeatures, lenient, 10);

        final JsonFormat.Value mergedValue = baseValue.withOverrides(overrideValue);
        final boolean overrideMatchesMergedValue = mergedValue.equals(overrideValue);

        assertTrue(overrideMatchesMergedValue);
        assertTrue(baseValue.hasPattern());
        assertEquals(JsonFormat.Shape.SCALAR, mergedValue.getShape());
        assertTrue(baseValue.hasShape());
        assertEquals(10, baseValue.getRadix());
        assertEquals("FALSE", mergedValue.getPattern());
    }
}
