package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test57 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies how {@link JsonFormat.Value} combines feature flags via
     * {@code withFeature}, {@code merge} and {@code withOverrides}, and that
     * adding an already-enabled feature returns the same instance.
     */
    @Test(timeout = 4000)
    public void test57() throws Throwable {
        JsonFormat.Value emptyValue = new JsonFormat.Value();
        JsonFormat.Feature readUnknownEnumAsNull =
                JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_AS_NULL;

        // Enabling the feature on the empty value produces a new value.
        JsonFormat.Value valueWithFeature = emptyValue.withFeature(readUnknownEnumAsNull);

        // Enabling the same feature again is a no-op: the same instance is returned.
        JsonFormat.Value valueWithFeatureAgain = valueWithFeature.withFeature(readUnknownEnumAsNull);
        assertSame(valueWithFeature, valueWithFeatureAgain);

        // Merging the feature-bearing value (base) with the empty value (overrides)
        // yields a distinct instance that is still equal to the feature-bearing value.
        JsonFormat.Value mergedValue = JsonFormat.Value.merge(valueWithFeature, emptyValue);
        assertNotSame(mergedValue, valueWithFeatureAgain);
        assertTrue(mergedValue.equals((Object) valueWithFeature));

        // Overriding with an equal value keeps the result equal to the original.
        JsonFormat.Value overriddenValue = valueWithFeatureAgain.withOverrides(mergedValue);
        assertTrue(overriddenValue.equals((Object) valueWithFeatureAgain));

        // No radix was ever specified, so the value keeps the default radix.
        assertFalse(valueWithFeatureAgain.hasNonDefaultRadix());
    }
}
