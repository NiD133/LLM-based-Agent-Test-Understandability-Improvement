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

    /**
     * Verifies that withFeature() produces a distinct Value with the feature enabled,
     * that withOverrides() using the feature-enabled value as override yields an
     * instance equal to that override, and that radix stays at its default (-1)
     * throughout these transformations.
     */
    @Test(timeout = 4000)
    public void test58() throws Throwable {
        // Base value with all defaults
        JsonFormat.Value baseValue = new JsonFormat.Value();

        // Apply a feature: read unknown enum values as null
        JsonFormat.Feature readUnknownEnumAsNull = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_AS_NULL;
        JsonFormat.Value valueWithFeature = baseValue.withFeature(readUnknownEnumAsNull);

        // Override base with the feature-enabled value
        JsonFormat.Value overriddenValue = baseValue.withOverrides(valueWithFeature);

        // withFeature() should produce a different value than the original
        assertFalse(valueWithFeature.equals((Object) baseValue));

        // Radix should remain at the default (-1) on the base value
        assertEquals((-1), baseValue.getRadix());

        // withOverrides() must return a new instance, not the base
        assertNotSame(overriddenValue, baseValue);

        // Radix should remain at the default (-1) after adding a feature
        assertEquals((-1), valueWithFeature.getRadix());

        // Overriding base with valueWithFeature should produce a value equal to valueWithFeature
        assertTrue(overriddenValue.equals((Object) valueWithFeature));
    }
}
