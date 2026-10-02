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
        // Create a default JsonFormat.Value with no features configured
        JsonFormat.Value baseValue = new JsonFormat.Value();

        // Enable READ_UNKNOWN_ENUM_VALUES_AS_NULL to produce a new value with that feature set
        JsonFormat.Feature readUnknownEnumsAsNull = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_AS_NULL;
        JsonFormat.Value valueWithFeature = baseValue.withFeature(readUnknownEnumsAsNull);

        // Apply the feature-enabled value as overrides on top of the base value
        JsonFormat.Value mergedValue = baseValue.withOverrides(valueWithFeature);

        // Enabling a feature produces a distinct value not equal to the unmodified base
        assertFalse(valueWithFeature.equals((Object) baseValue));

        // Both base and feature-enabled values retain the default radix (-1 = unset)
        assertEquals((-1), baseValue.getRadix());
        assertEquals((-1), valueWithFeature.getRadix());

        // Applying overrides returns a new object, not the same reference as the base
        assertNotSame(mergedValue, baseValue);

        // The merged result is equal to the overrides value since the base had no conflicting settings
        assertTrue(mergedValue.equals((Object) valueWithFeature));
    }
}
