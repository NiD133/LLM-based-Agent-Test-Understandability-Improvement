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
public class JsonFormat_ESTest_test52 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test52() throws Throwable {
        JsonFormat.Feature[] jsonFormat_FeatureArray0 = new JsonFormat.Feature[2];
        JsonFormat.Feature jsonFormat_Feature0 = JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED;
        jsonFormat_FeatureArray0[0] = jsonFormat_Feature0;
        jsonFormat_FeatureArray0[1] = jsonFormat_FeatureArray0[0];
        JsonFormat.Features jsonFormat_Features0 = JsonFormat.Features.construct(jsonFormat_FeatureArray0, jsonFormat_FeatureArray0);
        JsonFormat.Features jsonFormat_Features1 = jsonFormat_Features0.without(jsonFormat_FeatureArray0);
        assertNotSame(jsonFormat_Features1, jsonFormat_Features0);
        assertFalse(jsonFormat_Features1.equals((Object) jsonFormat_Features0));
    }
}
