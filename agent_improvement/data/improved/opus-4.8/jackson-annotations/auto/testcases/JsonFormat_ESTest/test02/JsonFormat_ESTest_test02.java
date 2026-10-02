package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test02 extends JsonFormat_ESTest_scaffolding {

    /**
     * When the same Feature appears in both the "enabled" and "disabled"
     * arrays passed to {@link JsonFormat.Features#construct}, the "disabled"
     * setting wins, so {@code get(...)} reports {@code false}. The test also
     * verifies that a {@link JsonFormat.Value} built with a null timezone has
     * no timezone and keeps the radix it was constructed with.
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        JsonFormat.Shape shape = JsonFormat.Shape.BINARY;

        // List the same feature as both enabled and disabled.
        JsonFormat.Feature feature = JsonFormat.Feature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE;
        JsonFormat.Feature[] featureList = { feature, feature, feature };

        JsonFormat.Features features = JsonFormat.Features.construct(featureList, featureList);

        // "Disabled" takes precedence over "enabled" for the same feature.
        Boolean featureEnabled = features.get(feature);
        assertFalse(featureEnabled);

        // Build a Value with a null timezone string and a radix of 3.
        JsonFormat.Value value = new JsonFormat.Value(
                "0(hYGYeE_-#<Q!B", shape, "0(hYGYeE_-#<Q!B", (String) null,
                features, featureEnabled, 3);

        assertFalse(value.hasTimeZone());
        assertEquals(3, value.getRadix());
    }
}
