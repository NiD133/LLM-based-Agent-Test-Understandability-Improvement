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
        JsonFormat.Shape binaryShape = JsonFormat.Shape.BINARY;
        JsonFormat.Feature enumDefaultValueFeature = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE;

        JsonFormat.Feature[] featureOverrides = new JsonFormat.Feature[6];
        featureOverrides[0] = enumDefaultValueFeature;
        featureOverrides[1] = enumDefaultValueFeature;
        featureOverrides[2] = enumDefaultValueFeature;
        featureOverrides[3] = featureOverrides[0];
        featureOverrides[4] = enumDefaultValueFeature;
        featureOverrides[5] = featureOverrides[2];

        JsonFormat.Features disabledFeatureOverrides =
                JsonFormat.Features.construct(featureOverrides, featureOverrides);
        Boolean disabledFeatureState = disabledFeatureOverrides.get(enumDefaultValueFeature);
        assertFalse(disabledFeatureState);

        JsonFormat.Value disabledValue = new JsonFormat.Value("skoH-w.W$", binaryShape,
                "FALSE", "skoH-w.W$", disabledFeatureOverrides, disabledFeatureState, 761);
        JsonFormat.Value enabledValue = disabledValue.withFeature(enumDefaultValueFeature);

        assertEquals(761, disabledValue.getRadix());
        assertNotSame(enabledValue, disabledValue);
        assertFalse(enabledValue.equals((Object) disabledValue));
        assertEquals(761, enabledValue.getRadix());
        assertEquals("skoH-w.W$", enabledValue.getPattern());
        assertTrue(enabledValue.hasLenient());
    }
}
