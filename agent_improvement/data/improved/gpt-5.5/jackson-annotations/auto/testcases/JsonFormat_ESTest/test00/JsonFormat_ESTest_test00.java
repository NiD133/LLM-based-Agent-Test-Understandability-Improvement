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
public class JsonFormat_ESTest_test00 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        JsonFormat.Shape numericShape = JsonFormat.Shape.NUMBER;
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        Boolean parsedLeniency = Boolean.valueOf("ADJUST_DATES_TO_CONTEXT_TIME_ZONE");

        JsonFormat.Value formatValue = new JsonFormat.Value(
                "ADJUST_DATES_TO_CONTEXT_TIME_ZONE",
                numericShape,
                "ADJUST_DATES_TO_CONTEXT_TIME_ZONE",
                "ADJUST_DATES_TO_CONTEXT_TIME_ZONE",
                emptyFeatures,
                parsedLeniency,
                115);

        assertEquals(JsonFormat.Shape.NUMBER, formatValue.getShape());
        assertTrue(formatValue.hasPattern());
        assertEquals(115, formatValue.getRadix());
    }
}
