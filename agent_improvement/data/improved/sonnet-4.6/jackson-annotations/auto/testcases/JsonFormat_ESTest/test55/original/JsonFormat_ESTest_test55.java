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
public class JsonFormat_ESTest_test55 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test55() throws Throwable {
        JsonFormat.Value jsonFormat_Value0 = JsonFormat.Value.empty();
        JsonFormat.Feature jsonFormat_Feature0 = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_AS_NULL;
        JsonFormat.Value jsonFormat_Value1 = jsonFormat_Value0.withFeature(jsonFormat_Feature0);
        JsonFormat.Feature jsonFormat_Feature1 = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE;
        JsonFormat.Value jsonFormat_Value2 = jsonFormat_Value0.withoutFeature(jsonFormat_Feature1);
        JsonFormat.Value jsonFormat_Value3 = jsonFormat_Value1.withOverrides(jsonFormat_Value2);
        assertFalse(jsonFormat_Value3.equals((Object) jsonFormat_Value2));
        assertNotSame(jsonFormat_Value3, jsonFormat_Value2);
        assertEquals((-1), jsonFormat_Value3.getRadix());
        assertNotSame(jsonFormat_Value3, jsonFormat_Value1);
        assertFalse(jsonFormat_Value3.equals((Object) jsonFormat_Value1));
        assertFalse(jsonFormat_Value2.hasNonDefaultRadix());
    }
}
