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
        JsonFormat.Shape numberShape = JsonFormat.Shape.NUMBER;
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        // Boolean.valueOf with a non-"true" string yields Boolean.FALSE
        Boolean lenientFlag = Boolean.valueOf("ADJUST_DATES_TO_CONTEXT_TIME_ZONE");

        JsonFormat.Value formatValue = new JsonFormat.Value(
            "ADJUST_DATES_TO_CONTEXT_TIME_ZONE",  // pattern
            numberShape,                            // shape
            "ADJUST_DATES_TO_CONTEXT_TIME_ZONE",  // locale string
            "ADJUST_DATES_TO_CONTEXT_TIME_ZONE",  // timezone string
            emptyFeatures,                          // features
            lenientFlag,                            // lenient
            115                                     // radix
        );

        assertEquals(JsonFormat.Shape.NUMBER, formatValue.getShape());
        assertTrue(formatValue.hasPattern());
        assertEquals(115, formatValue.getRadix());
    }
}
