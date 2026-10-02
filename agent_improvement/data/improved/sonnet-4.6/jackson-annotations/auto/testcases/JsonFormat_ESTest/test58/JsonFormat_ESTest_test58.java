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
public class JsonFormat_ESTest_test58 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test58() throws Throwable {
        // Base value with all defaults (radix = -1, no features set)
        JsonFormat.Value baseValue = new JsonFormat.Value();

        // Derive a new value with READ_UNKNOWN_ENUM_VALUES_AS_NULL enabled
        JsonFormat.Value valueWithFeature = baseValue.withFeature(JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);

        // Applying the feature-enriched value as overrides on the base value should
        // produce a result equivalent to the feature-enriched value
        JsonFormat.Value overriddenValue = baseValue.withOverrides(valueWithFeature);

        // Adding a feature must produce a distinct value (not equal to the featureless base)
        assertFalse(valueWithFeature.equals((Object) baseValue));

        // Both the base value and the feature-enriched value use the default radix (-1)
        assertEquals((-1), baseValue.getRadix());
        assertEquals((-1), valueWithFeature.getRadix());

        // withOverrides returns a new instance, not the original base
        assertNotSame(overriddenValue, baseValue);

        // The overridden result should be equal to the value that was applied as overrides
        assertTrue(overriddenValue.equals((Object) valueWithFeature));
    }
}
