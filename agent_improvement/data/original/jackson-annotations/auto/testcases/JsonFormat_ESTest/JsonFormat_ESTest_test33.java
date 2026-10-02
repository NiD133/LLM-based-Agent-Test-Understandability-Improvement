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
public class JsonFormat_ESTest_test33 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test33() throws Throwable {
        JsonFormat.Shape jsonFormat_Shape0 = JsonFormat.Shape.NUMBER_INT;
        JsonFormat.Value jsonFormat_Value0 = JsonFormat.Value.forPattern("");
        SimpleTimeZone simpleTimeZone0 = new SimpleTimeZone((-5461), "");
        JsonFormat.Features jsonFormat_Features0 = JsonFormat.Features.empty();
        Boolean boolean0 = Boolean.FALSE;
        JsonFormat.Value jsonFormat_Value1 = new JsonFormat.Value("", jsonFormat_Shape0, (Locale) null, "", simpleTimeZone0, jsonFormat_Features0, boolean0, (-1685));
        JsonFormat.Value jsonFormat_Value2 = jsonFormat_Value0.withOverrides(jsonFormat_Value1);
        assertEquals((-1685), jsonFormat_Value2.getRadix());
        assertTrue(jsonFormat_Value2.hasLenient());
        assertFalse(jsonFormat_Value0.hasNonDefaultRadix());
        assertTrue(jsonFormat_Value2.hasShape());
        assertFalse(jsonFormat_Value2.equals((Object) jsonFormat_Value1));
        assertFalse(jsonFormat_Value2.hasTimeZone());
        assertTrue(jsonFormat_Value1.hasShape());
    }
}
