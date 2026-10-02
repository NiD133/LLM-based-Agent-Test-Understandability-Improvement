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
public class JsonFormat_ESTest_test10 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        JsonFormat.Shape scalarShape = JsonFormat.Shape.SCALAR;
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        Boolean lenient = Boolean.TRUE;
        JsonFormat.Value formatValue = new JsonFormat.Value("FALSE", scalarShape, "FALSE", "O", emptyFeatures, lenient, 10);

        HashMap<String, List<String>> valuesByName = new HashMap<String, List<String>>();
        valuesByName.remove((Object) formatValue);

        assertEquals(10, formatValue.getRadix());
        assertEquals("O", formatValue.timeZoneAsString());
        assertEquals("FALSE", formatValue.getPattern());
        assertTrue(formatValue.hasShape());
        assertTrue(formatValue.hasPattern());
    }
}
