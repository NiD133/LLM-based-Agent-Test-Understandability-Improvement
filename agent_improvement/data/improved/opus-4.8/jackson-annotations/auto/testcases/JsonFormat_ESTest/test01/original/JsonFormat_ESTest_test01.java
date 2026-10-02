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
public class JsonFormat_ESTest_test01 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        JsonFormat.Shape jsonFormat_Shape0 = JsonFormat.Shape.OBJECT;
        JsonFormat.Value jsonFormat_Value0 = new JsonFormat.Value("FALSE", jsonFormat_Shape0, "JsonFormat.Value(pattern=,shape=ANY,lenient=null,locale=null,timezone=null,features=EMPTY,radix=-1639)", "8e.", (JsonFormat.Features) null, (Boolean) null, (-1639));
        assertEquals("8e.", jsonFormat_Value0.timeZoneAsString());
        assertEquals((-1639), jsonFormat_Value0.getRadix());
        assertEquals("FALSE", jsonFormat_Value0.getPattern());
        assertEquals(JsonFormat.Shape.OBJECT, jsonFormat_Value0.getShape());
    }
}
