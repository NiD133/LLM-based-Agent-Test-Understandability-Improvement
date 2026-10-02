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

    @Test(timeout = 4000)
    public void test42() throws Throwable {
        JsonFormat.Shape jsonFormat_Shape0 = JsonFormat.Shape.OBJECT;
        JsonFormat.Value jsonFormat_Value0 = new JsonFormat.Value("##default", jsonFormat_Shape0, "##default", "##default", (JsonFormat.Features) null, (Boolean) null, (-1880944581));
        assertFalse(jsonFormat_Value0.hasTimeZone());
        assertTrue(jsonFormat_Value0.hasPattern());
        assertEquals((-1880944581), jsonFormat_Value0.getRadix());
        assertEquals(JsonFormat.Shape.OBJECT, jsonFormat_Value0.getShape());
    }
}
