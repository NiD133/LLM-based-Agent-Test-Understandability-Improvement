package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test54 extends JsonFormat_ESTest_scaffolding {

    /**
     * When a Feature is listed in both the "enabled" and "disabled" arrays passed
     * to Features.construct, the disabled side wins, so Features.get returns FALSE.
     * Building a Value around such Features and then calling withFeature(...) yields
     * a distinct, non-equal Value that preserves the original radix, pattern and
     * lenient flag.
     */
    @Test(timeout = 4000)
    public void withFeatureReturnsDistinctValuePreservingScalarFields() throws Throwable {
        JsonFormat.Feature feature = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE;

        // Same feature appears in both the enabled and disabled lists.
        JsonFormat.Feature[] features = {
            feature, feature, feature, feature, feature, feature
        };
        JsonFormat.Features featureSet = JsonFormat.Features.construct(features, features);

        // Disabled takes precedence over enabled, so the feature reads as FALSE.
        Boolean featureState = featureSet.get(feature);
        assertFalse(featureState);

        // featureState (FALSE) is reused here as the "lenient" flag.
        JsonFormat.Value originalValue = new JsonFormat.Value(
                "skoH-w.W$", JsonFormat.Shape.BINARY, "FALSE", "skoH-w.W$",
                featureSet, featureState, 761);

        JsonFormat.Value valueWithFeature = originalValue.withFeature(feature);

        assertEquals(761, originalValue.getRadix());
        assertNotSame(valueWithFeature, originalValue);
        assertFalse(valueWithFeature.equals((Object) originalValue));
        assertEquals(761, valueWithFeature.getRadix());
        assertEquals("skoH-w.W$", valueWithFeature.getPattern());
        assertTrue(valueWithFeature.hasLenient());
    }
}
