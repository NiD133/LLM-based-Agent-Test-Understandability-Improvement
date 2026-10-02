package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test82 extends JsonFormat_ESTest_scaffolding {

    /**
     * A Value created via {@link JsonFormat.Value#forLeniency(boolean)} only sets the
     * leniency flag. Verifies that all other properties keep their defaults:
     * no time zone, no explicit shape, non-lenient (since leniency was set to false),
     * and the default radix.
     */
    @Test(timeout = 4000)
    public void forLeniencyFalse_leavesOtherPropertiesAtDefaults() throws Throwable {
        JsonFormat.Value nonLenientFormat = JsonFormat.Value.forLeniency(false);

        String timeZone = nonLenientFormat.timeZoneAsString();

        assertNull("no time zone should be configured", timeZone);
        assertFalse("shape should remain the default (ANY)", nonLenientFormat.hasShape());
        assertFalse("leniency was explicitly set to false", nonLenientFormat.isLenient());
        assertFalse("radix should remain the default", nonLenientFormat.hasNonDefaultRadix());
    }
}
