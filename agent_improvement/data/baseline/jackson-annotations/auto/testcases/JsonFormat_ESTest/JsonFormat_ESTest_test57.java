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
public class JsonFormat_ESTest_test57 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test57() throws Throwable {
        JsonFormat.Value jsonFormat_Value0 = new JsonFormat.Value();
        JsonFormat.Feature jsonFormat_Feature0 = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_AS_NULL;
        JsonFormat.Value jsonFormat_Value1 = jsonFormat_Value0.withFeature(jsonFormat_Feature0);
        JsonFormat.Value jsonFormat_Value2 = jsonFormat_Value1.withFeature(jsonFormat_Feature0);
        JsonFormat.Value jsonFormat_Value3 = JsonFormat.Value.merge(jsonFormat_Value1, jsonFormat_Value0);
        JsonFormat.Value jsonFormat_Value4 = jsonFormat_Value2.withOverrides(jsonFormat_Value3);
        assertNotSame(jsonFormat_Value3, jsonFormat_Value2);
        assertTrue(jsonFormat_Value4.equals((Object) jsonFormat_Value2));
        assertTrue(jsonFormat_Value3.equals((Object) jsonFormat_Value1));
        assertFalse(jsonFormat_Value2.hasNonDefaultRadix());
        assertSame(jsonFormat_Value2, jsonFormat_Value1);
    }
}
