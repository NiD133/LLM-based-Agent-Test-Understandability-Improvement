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
public class JsonFormat_ESTest_test41 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test41() throws Throwable {
        JsonFormat.Shape jsonFormat_Shape0 = JsonFormat.Shape.SCALAR;
        Locale locale0 = Locale.ITALIAN;
        TimeZone timeZone0 = TimeZone.getDefault();
        Boolean boolean0 = new Boolean((String) null);
        JsonFormat.Value jsonFormat_Value0 = new JsonFormat.Value((String) null, jsonFormat_Shape0, locale0, timeZone0, (JsonFormat.Features) null, boolean0, (-2562));
        assertEquals((-2562), jsonFormat_Value0.getRadix());
        assertEquals(JsonFormat.Shape.SCALAR, jsonFormat_Value0.getShape());
    }
}
