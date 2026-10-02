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
public class JsonFormat_ESTest_test04 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        JsonFormat.Value jsonFormat_Value0 = new JsonFormat.Value();
        JsonFormat.Value jsonFormat_Value1 = JsonFormat.Value.forPattern("*h,2D`=nR6aV]Mg'.");
        boolean boolean0 = jsonFormat_Value1.equals(jsonFormat_Value0);
        assertFalse(jsonFormat_Value1.hasShape());
        assertFalse(boolean0);
        assertFalse(jsonFormat_Value1.hasNonDefaultRadix());
        assertFalse(jsonFormat_Value1.hasTimeZone());
    }
}
