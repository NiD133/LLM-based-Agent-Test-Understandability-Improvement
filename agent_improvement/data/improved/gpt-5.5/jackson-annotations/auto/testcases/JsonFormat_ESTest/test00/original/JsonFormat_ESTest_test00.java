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
        JsonFormat.Shape jsonFormat_Shape0 = JsonFormat.Shape.NUMBER;
        JsonFormat.Features jsonFormat_Features0 = JsonFormat.Features.empty();
        Boolean boolean0 = Boolean.valueOf("ADJUST_DATES_TO_CONTEXT_TIME_ZONE");
        JsonFormat.Value jsonFormat_Value0 = new JsonFormat.Value("ADJUST_DATES_TO_CONTEXT_TIME_ZONE", jsonFormat_Shape0, "ADJUST_DATES_TO_CONTEXT_TIME_ZONE", "ADJUST_DATES_TO_CONTEXT_TIME_ZONE", jsonFormat_Features0, boolean0, 115);
        assertEquals(JsonFormat.Shape.NUMBER, jsonFormat_Value0.getShape());
        assertTrue(jsonFormat_Value0.hasPattern());
        assertEquals(115, jsonFormat_Value0.getRadix());
    }
}
