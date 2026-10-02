package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test53 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that disabling a feature on a default {@link JsonFormat.Value} is
     * idempotent: the first {@code withoutFeature} call produces a new Value (the
     * feature state actually changed), while a second call with the same feature
     * returns the very same instance because nothing changes.
     */
    @Test(timeout = 4000)
    public void disablingSameFeatureTwiceReturnsSameInstance() throws Throwable {
        JsonFormat.Value defaultValue = new JsonFormat.Value();
        JsonFormat.Feature feature = JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED;

        // First disable: produces a new Value distinct from the default.
        JsonFormat.Value afterFirstDisable = defaultValue.withoutFeature(feature);
        // Second disable of the already-disabled feature: no change, same instance returned.
        JsonFormat.Value afterSecondDisable = afterFirstDisable.withoutFeature(feature);

        assertNotSame(afterSecondDisable, defaultValue);
        assertSame(afterSecondDisable, afterFirstDisable);

        // Radix is untouched, so it stays at the default for every Value involved.
        assertFalse(defaultValue.hasNonDefaultRadix());
        assertFalse(afterSecondDisable.hasNonDefaultRadix());
    }
}
