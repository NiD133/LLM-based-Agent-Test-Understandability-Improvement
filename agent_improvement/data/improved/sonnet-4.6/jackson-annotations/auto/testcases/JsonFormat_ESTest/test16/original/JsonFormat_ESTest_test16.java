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
public class JsonFormat_ESTest_test16 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        JsonFormat.Shape jsonFormat_Shape0 = JsonFormat.Shape.SCALAR;
        JsonFormat.Features jsonFormat_Features0 = JsonFormat.Features.empty();
        Boolean boolean0 = Boolean.TRUE;
        JsonFormat.Value jsonFormat_Value0 = new JsonFormat.Value("FALSE", jsonFormat_Shape0, "FALSE", "O", jsonFormat_Features0, boolean0, 10);
        boolean boolean1 = jsonFormat_Value0.hasTimeZone();
        assertEquals("FALSE", jsonFormat_Value0.getPattern());
        assertEquals(10, jsonFormat_Value0.getRadix());
        assertEquals("O", jsonFormat_Value0.timeZoneAsString());
        assertTrue(jsonFormat_Value0.hasShape());
        assertTrue(boolean1);
    }
}
