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
public class JsonFormat_ESTest_test02 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        JsonFormat.Shape jsonFormat_Shape0 = JsonFormat.Shape.BINARY;
        JsonFormat.Feature[] jsonFormat_FeatureArray0 = new JsonFormat.Feature[3];
        JsonFormat.Feature jsonFormat_Feature0 = JsonFormat.Feature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE;
        jsonFormat_FeatureArray0[0] = jsonFormat_Feature0;
        jsonFormat_FeatureArray0[1] = jsonFormat_FeatureArray0[0];
        jsonFormat_FeatureArray0[2] = jsonFormat_FeatureArray0[0];
        JsonFormat.Features jsonFormat_Features0 = JsonFormat.Features.construct(jsonFormat_FeatureArray0, jsonFormat_FeatureArray0);
        Boolean boolean0 = jsonFormat_Features0.get(jsonFormat_Feature0);
        assertFalse(boolean0);
        JsonFormat.Value jsonFormat_Value0 = new JsonFormat.Value("0(hYGYeE_-#<Q!B", jsonFormat_Shape0, "0(hYGYeE_-#<Q!B", (String) null, jsonFormat_Features0, boolean0, 3);
        assertFalse(jsonFormat_Value0.hasTimeZone());
        assertEquals(3, jsonFormat_Value0.getRadix());
    }
}
