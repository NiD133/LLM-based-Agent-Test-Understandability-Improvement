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
public class JsonFormat_ESTest_test06 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        JsonFormat.Shape jsonFormat_Shape0 = JsonFormat.Shape.ARRAY;
        JsonFormat.Value jsonFormat_Value0 = JsonFormat.Value.forShape(jsonFormat_Shape0);
        JsonFormat.Value jsonFormat_Value1 = JsonFormat.Value.forLeniency(false);
        boolean boolean0 = jsonFormat_Value0.equals(jsonFormat_Value1);
        assertFalse(jsonFormat_Value1.isLenient());
        assertFalse(boolean0);
        assertEquals((-1), jsonFormat_Value1.getRadix());
        assertFalse(jsonFormat_Value1.hasShape());
        assertFalse(jsonFormat_Value0.hasNonDefaultRadix());
    }
}
