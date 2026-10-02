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
        // Construct Features where READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE appears in
        // both the "enabled" and "disabled" arrays. The disabled check takes priority in
        // Features.get(), so the result is Boolean.FALSE.
        JsonFormat.Feature feature = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE;
        JsonFormat.Feature[] allSameFeature = new JsonFormat.Feature[6];
        for (int i = 0; i < allSameFeature.length; i++) {
            allSameFeature[i] = feature;
        }
        JsonFormat.Features features = JsonFormat.Features.construct(allSameFeature, allSameFeature);

        // Disabled wins over enabled, so get() returns false
        Boolean featureState = features.get(feature);
        assertFalse(featureState);

        // Build a Value with: a non-default radix, BINARY shape, explicit lenient=false,
        // and the features constructed above (feature is currently disabled).
        JsonFormat.Shape shape = JsonFormat.Shape.BINARY;
        int radix = 761;
        JsonFormat.Value original = new JsonFormat.Value("skoH-w.W$", shape, "FALSE", "skoH-w.W$", features, featureState, radix);

        // withFeature() enables the feature, producing a new Value that differs from original
        JsonFormat.Value withFeatureEnabled = original.withFeature(feature);

        assertEquals(761, original.getRadix());
        assertNotSame(withFeatureEnabled, original);
        // The new Value has the feature enabled while original had it disabled — not equal
        assertFalse(withFeatureEnabled.equals((Object) original));
        assertEquals(761, withFeatureEnabled.getRadix());
        assertEquals("skoH-w.W$", withFeatureEnabled.getPattern());
        // lenient was set to Boolean.FALSE (non-null), so hasLenient() is true
        assertTrue(withFeatureEnabled.hasLenient());
    }
}
