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
     * A Value created only with a leniency flag should carry no time zone,
     * no shape, and a default radix.
     */
    @Test(timeout = 4000)
    public void forLeniency_leavesTimeZoneAndOtherSettingsUnset() throws Throwable {
        JsonFormat.Value leniencyOnly = JsonFormat.Value.forLeniency(false);

        String timeZone = leniencyOnly.timeZoneAsString();

        assertNull("No time zone was configured", timeZone);
        assertFalse("Leniency was explicitly set to false", leniencyOnly.isLenient());
        assertFalse("No explicit shape was configured", leniencyOnly.hasShape());
        assertFalse("Radix should remain at its default", leniencyOnly.hasNonDefaultRadix());
    }
}
