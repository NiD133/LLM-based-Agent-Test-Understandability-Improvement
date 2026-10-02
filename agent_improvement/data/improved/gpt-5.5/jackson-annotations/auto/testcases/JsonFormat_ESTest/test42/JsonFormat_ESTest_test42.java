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
public class JsonFormat_ESTest_test42 extends JsonFormat_ESTest_scaffolding {

    private static final String DEFAULT_FORMAT_MARKER = "##default";
    private static final int CUSTOM_RADIX = -1880944581;

    @Test(timeout = 4000)
    public void test42() throws Throwable {
        JsonFormat.Shape objectShape = JsonFormat.Shape.OBJECT;
        JsonFormat.Value formatValue = new JsonFormat.Value(
                DEFAULT_FORMAT_MARKER,
                objectShape,
                DEFAULT_FORMAT_MARKER,
                DEFAULT_FORMAT_MARKER,
                (JsonFormat.Features) null,
                (Boolean) null,
                CUSTOM_RADIX);

        assertFalse(formatValue.hasTimeZone());
        assertTrue(formatValue.hasPattern());
        assertEquals(CUSTOM_RADIX, formatValue.getRadix());
        assertEquals(JsonFormat.Shape.OBJECT, formatValue.getShape());
    }
}
