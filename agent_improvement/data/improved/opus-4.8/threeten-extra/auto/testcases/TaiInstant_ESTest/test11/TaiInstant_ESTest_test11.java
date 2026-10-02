package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test11 extends TaiInstant_ESTest_scaffolding {

    /**
     * Adding a zero duration to a TaiInstant should be a no-op: the method
     * returns the very same instance, leaving its seconds and nanos unchanged.
     *
     * The starting instant is built from ofTaiSeconds(-10, -10). The factory
     * normalises the negative nanosecond adjustment so that nanos stay in the
     * range 0..999,999,999, borrowing one second in the process:
     *   seconds = -10 + floorDiv(-10, 1_000_000_000) = -10 + (-1) = -11
     *   nanos   = floorMod(-10, 1_000_000_000)        = 999,999,990
     */
    @Test(timeout = 4000)
    public void plusZeroDurationReturnsSameInstanceAndKeepsNormalisedFields() throws Throwable {
        TaiInstant startInstant = TaiInstant.ofTaiSeconds(-10L, -10L);

        TaiInstant result = startInstant.plus(Duration.ZERO);

        assertSame("plus(ZERO) should return the same immutable instance", result, startInstant);
        assertEquals(-11L, result.getTaiSeconds());
        assertEquals(999999990, result.getNano());
    }
}
