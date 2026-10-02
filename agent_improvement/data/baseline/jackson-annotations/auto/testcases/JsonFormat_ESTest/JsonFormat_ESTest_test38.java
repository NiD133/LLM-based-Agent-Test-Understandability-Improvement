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
public class JsonFormat_ESTest_test38 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test38() throws Throwable {
        JsonFormat.Shape jsonFormat_Shape0 = JsonFormat.Shape.OBJECT;
        JsonFormat.Features jsonFormat_Features0 = JsonFormat.Features.empty();
        Boolean boolean0 = Boolean.valueOf(false);
        JsonFormat.Value jsonFormat_Value0 = new JsonFormat.Value("`K22BIe$=Oc|vAr4!T", jsonFormat_Shape0, "7", "7", jsonFormat_Features0, boolean0, 4024);
        JsonFormat.Value[] jsonFormat_ValueArray0 = new JsonFormat.Value[9];
        jsonFormat_ValueArray0[0] = jsonFormat_Value0;
        jsonFormat_ValueArray0[1] = jsonFormat_ValueArray0[0];
        JsonFormat.Value jsonFormat_Value1 = JsonFormat.Value.mergeAll(jsonFormat_ValueArray0);
        assertEquals(4024, jsonFormat_Value1.getRadix());
        assertEquals("7", jsonFormat_Value1.timeZoneAsString());
        assertNotNull(jsonFormat_Value1);
        assertSame(jsonFormat_Value1, jsonFormat_Value0);
        assertEquals(JsonFormat.Shape.OBJECT, jsonFormat_Value1.getShape());
        assertEquals("`K22BIe$=Oc|vAr4!T", jsonFormat_Value1.getPattern());
    }
}
