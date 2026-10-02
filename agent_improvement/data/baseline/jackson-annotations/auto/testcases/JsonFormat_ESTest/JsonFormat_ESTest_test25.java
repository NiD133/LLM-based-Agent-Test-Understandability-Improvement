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
public class JsonFormat_ESTest_test25 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test25() throws Throwable {
        JsonFormat.Shape jsonFormat_Shape0 = JsonFormat.Shape.NUMBER_FLOAT;
        TimeZone timeZone0 = TimeZone.getTimeZone("-d`");
        JsonFormat.Features jsonFormat_Features0 = JsonFormat.Features.empty();
        Boolean boolean0 = Boolean.valueOf("com.fasterxml.jackson.annotation.JsonFormat$Features");
        JsonFormat.Value jsonFormat_Value0 = new JsonFormat.Value("GTN#0o7A|nNN5e%^;@", jsonFormat_Shape0, (Locale) null, timeZone0, jsonFormat_Features0, boolean0, 1932);
        TimeZone timeZone1 = jsonFormat_Value0.getTimeZone();
        assertEquals(1932, jsonFormat_Value0.getRadix());
        assertTrue(jsonFormat_Value0.hasShape());
        assertNotNull(timeZone1);
        assertEquals("GTN#0o7A|nNN5e%^;@", jsonFormat_Value0.getPattern());
    }
}
