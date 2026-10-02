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
public class JsonFormat_ESTest_test57 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies the idempotency of withFeature and the behaviour of merge/withOverrides
     * when combining Values that carry the READ_UNKNOWN_ENUM_VALUES_AS_NULL feature.
     *
     * Key observations:
     *  - Enabling an already-enabled feature returns the exact same Value instance.
     *  - merge(base, emptyOverride) produces a Value logically equal to the base.
     *  - withOverrides with an equal-valued override produces a logically equal result.
     */
    @Test(timeout = 4000)
    public void test57() throws Throwable {
        // A default Value with no settings configured
        JsonFormat.Value defaultValue = new JsonFormat.Value();

        JsonFormat.Feature readUnknownEnumAsNull = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_AS_NULL;

        // Enable the feature on the default value — produces a new instance
        JsonFormat.Value valueWithFeature = defaultValue.withFeature(readUnknownEnumAsNull);

        // Enabling the same feature again is idempotent: same instance is returned
        JsonFormat.Value valueWithFeatureAgain = valueWithFeature.withFeature(readUnknownEnumAsNull);

        // Merging valueWithFeature (base) with the empty defaultValue (override) keeps the feature
        JsonFormat.Value mergedValue = JsonFormat.Value.merge(valueWithFeature, defaultValue);

        // Applying mergedValue as overrides onto valueWithFeatureAgain (which equals valueWithFeature)
        JsonFormat.Value overriddenValue = valueWithFeatureAgain.withOverrides(mergedValue);

        // mergedValue and valueWithFeatureAgain are distinct instances (merge created a new object)
        assertNotSame(mergedValue, valueWithFeatureAgain);

        // overriddenValue is logically equal to valueWithFeatureAgain (feature is already present)
        assertTrue(overriddenValue.equals((Object) valueWithFeatureAgain));

        // mergedValue is logically equal to valueWithFeature (empty override doesn't change anything)
        assertTrue(mergedValue.equals((Object) valueWithFeature));

        // No custom radix was ever set, so hasNonDefaultRadix() must return false
        assertFalse(valueWithFeatureAgain.hasNonDefaultRadix());

        // Idempotency check: enabling an already-enabled feature returns the same instance
        assertSame(valueWithFeatureAgain, valueWithFeature);
    }
}
