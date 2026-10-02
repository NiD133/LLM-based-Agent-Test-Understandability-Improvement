package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test20 extends TaiInstant_ESTest_scaffolding {

    /**
     * Verifies that toString() renders a TaiInstant as
     * "{seconds}.{nanos}s(TAI)" with the seconds shown as-is and the
     * nanoseconds zero-padded to exactly nine digits.
     */
    @Test(timeout = 4000)
    public void toString_formatsSecondsAndNineDigitNanos() throws Throwable {
        long taiSeconds = 20L;
        long nanoAdjustment = 20L;
        TaiInstant instant = TaiInstant.ofTaiSeconds(taiSeconds, nanoAdjustment);

        String formatted = instant.toString();

        assertEquals("20.000000020s(TAI)", formatted);
    }
}
