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
        JsonFormat.Shape jsonFormat_Shape0 = JsonFormat.Shape.NATURAL;
        Locale locale0 = Locale.ITALY;
        TimeZone timeZone0 = TimeZone.getDefault();
        JsonFormat.Value jsonFormat_Value0 = new JsonFormat.Value(".sT6~u!yfoEV'=Itz ", jsonFormat_Shape0, locale0, timeZone0, (JsonFormat.Features) null, (Boolean) null, 95);
        boolean boolean0 = jsonFormat_Value0.hasNonDefaultRadix();
        assertTrue(jsonFormat_Value0.hasPattern());
        assertEquals(95, jsonFormat_Value0.getRadix());
        assertEquals(JsonFormat.Shape.NATURAL, jsonFormat_Value0.getShape());
        assertTrue(boolean0);
    }
}
