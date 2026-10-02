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
        JsonFormat.Shape binaryShape = JsonFormat.Shape.BINARY;
        JsonFormat.Feature disabledFeature = JsonFormat.Feature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE;

        JsonFormat.Feature[] duplicateFeatureFlags = new JsonFormat.Feature[3];
        duplicateFeatureFlags[0] = disabledFeature;
        duplicateFeatureFlags[1] = duplicateFeatureFlags[0];
        duplicateFeatureFlags[2] = duplicateFeatureFlags[0];

        JsonFormat.Features features = JsonFormat.Features.construct(duplicateFeatureFlags, duplicateFeatureFlags);
        Boolean featureState = features.get(disabledFeature);
        assertFalse(featureState);

        JsonFormat.Value value = new JsonFormat.Value("0(hYGYeE_-#<Q!B", binaryShape,
                "0(hYGYeE_-#<Q!B", (String) null, features, featureState, 3);
        assertFalse(value.hasTimeZone());
        assertEquals(3, value.getRadix());
    }
}
