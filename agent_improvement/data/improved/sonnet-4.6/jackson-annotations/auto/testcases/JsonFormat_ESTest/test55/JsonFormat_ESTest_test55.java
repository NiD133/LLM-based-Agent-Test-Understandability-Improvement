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
public class JsonFormat_ESTest_test55 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test55() throws Throwable {
        // Base: an empty Value with no features set
        JsonFormat.Value baseValue = JsonFormat.Value.empty();

        // Derive a value with READ_UNKNOWN_ENUM_VALUES_AS_NULL explicitly enabled
        JsonFormat.Feature readUnknownAsNull = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_AS_NULL;
        JsonFormat.Value valueWithNullFeatureEnabled = baseValue.withFeature(readUnknownAsNull);

        // Derive another value (from the same base) with READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE explicitly disabled
        JsonFormat.Feature readUnknownUsingDefault = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE;
        JsonFormat.Value valueWithDefaultFeatureDisabled = baseValue.withoutFeature(readUnknownUsingDefault);

        // Merge: apply valueWithDefaultFeatureDisabled as an override on top of valueWithNullFeatureEnabled.
        // The result should carry the enabled READ_UNKNOWN_AS_NULL feature AND the disabled
        // READ_UNKNOWN_USING_DEFAULT feature, making it distinct from both source values.
        JsonFormat.Value mergedValue = valueWithNullFeatureEnabled.withOverrides(valueWithDefaultFeatureDisabled);

        // Merged result differs from the override-only value (it also has the enabled feature from valueWithNullFeatureEnabled)
        assertFalse(mergedValue.equals((Object) valueWithDefaultFeatureDisabled));
        assertNotSame(mergedValue, valueWithDefaultFeatureDisabled);

        // Radix is not set in any of these values, so it stays at the default (-1)
        assertEquals((-1), mergedValue.getRadix());

        // Merged result also differs from the base-with-enabled-feature value (it additionally has the disabled feature)
        assertNotSame(mergedValue, valueWithNullFeatureEnabled);
        assertFalse(mergedValue.equals((Object) valueWithNullFeatureEnabled));

        // The override value itself has no non-default radix configured
        assertFalse(valueWithDefaultFeatureDisabled.hasNonDefaultRadix());
    }
}
