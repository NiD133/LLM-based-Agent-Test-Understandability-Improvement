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
public class JsonFormat_ESTest_test54 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test54() throws Throwable {
        JsonFormat.Shape jsonFormat_Shape0 = JsonFormat.Shape.BINARY;
        JsonFormat.Feature[] jsonFormat_FeatureArray0 = new JsonFormat.Feature[6];
        JsonFormat.Feature jsonFormat_Feature0 = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE;
        jsonFormat_FeatureArray0[0] = jsonFormat_Feature0;
        jsonFormat_FeatureArray0[1] = jsonFormat_Feature0;
        jsonFormat_FeatureArray0[2] = jsonFormat_Feature0;
        jsonFormat_FeatureArray0[3] = jsonFormat_FeatureArray0[0];
        jsonFormat_FeatureArray0[4] = jsonFormat_Feature0;
        jsonFormat_FeatureArray0[5] = jsonFormat_FeatureArray0[2];
        JsonFormat.Features jsonFormat_Features0 = JsonFormat.Features.construct(jsonFormat_FeatureArray0, jsonFormat_FeatureArray0);
        Boolean boolean0 = jsonFormat_Features0.get(jsonFormat_Feature0);
        assertFalse(boolean0);
        JsonFormat.Value jsonFormat_Value0 = new JsonFormat.Value("skoH-w.W$", jsonFormat_Shape0, "FALSE", "skoH-w.W$", jsonFormat_Features0, boolean0, 761);
        JsonFormat.Value jsonFormat_Value1 = jsonFormat_Value0.withFeature(jsonFormat_Feature0);
        assertEquals(761, jsonFormat_Value0.getRadix());
        assertNotSame(jsonFormat_Value1, jsonFormat_Value0);
        assertFalse(jsonFormat_Value1.equals((Object) jsonFormat_Value0));
        assertEquals(761, jsonFormat_Value1.getRadix());
        assertEquals("skoH-w.W$", jsonFormat_Value1.getPattern());
        assertTrue(jsonFormat_Value1.hasLenient());
    }
}
