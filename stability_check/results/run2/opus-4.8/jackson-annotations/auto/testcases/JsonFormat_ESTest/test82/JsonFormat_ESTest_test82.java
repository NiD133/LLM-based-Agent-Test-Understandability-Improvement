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
     * A Value created only with a leniency flag (via forLeniency) should carry
     * no timezone, no shape and no custom radix. This verifies that
     * timeZoneAsString() returns null when no timezone was configured, and that
     * the leniency-only Value keeps all other format attributes at their defaults.
     */
    @Test(timeout = 4000)
    public void leniencyOnlyValueHasNoTimeZoneShapeOrRadix() throws Throwable {
        JsonFormat.Value leniencyOnlyValue = JsonFormat.Value.forLeniency(false);

        String timeZone = leniencyOnlyValue.timeZoneAsString();

        assertNull("no timezone was configured", timeZone);
        assertFalse("shape stays at default (ANY)", leniencyOnlyValue.hasShape());
        assertFalse("leniency was explicitly set to false", leniencyOnlyValue.isLenient());
        assertFalse("radix stays at default", leniencyOnlyValue.hasNonDefaultRadix());
    }
}
