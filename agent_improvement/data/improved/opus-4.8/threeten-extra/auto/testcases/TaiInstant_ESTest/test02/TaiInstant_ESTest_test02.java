package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test02 extends TaiInstant_ESTest_scaffolding {

    /**
     * A negative nanosecond adjustment should be normalized so that the stored
     * nano-of-second is always in the range 0..999,999,999, borrowing one whole
     * second from the seconds field. Starting from (-1 second, -1 nanosecond),
     * the result is (-2 seconds, 999,999,999 nanoseconds).
     * Also verifies that equals() returns false when compared with a non-TaiInstant.
     */
    @Test(timeout = 4000)
    public void normalizesNegativeNanoAdjustmentAndRejectsForeignType() throws Throwable {
        TaiInstant instant = TaiInstant.ofTaiSeconds(-1L, -1L);

        assertEquals(-2L, instant.getTaiSeconds());
        assertEquals(999999999, instant.getNano());

        boolean equalsPlainObject = instant.equals(new Object());
        assertFalse(equalsPlainObject);
    }
}
