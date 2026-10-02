package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test03 extends TaiInstant_ESTest_scaffolding {

    /**
     * Verifies that:
     * <ul>
     *   <li>{@link TaiInstant#ofTaiSeconds(long, long)} normalises a negative nanosecond
     *       adjustment by borrowing one second, so the stored nanosecond stays within
     *       the range 0..999,999,999.</li>
     *   <li>{@link TaiInstant#equals(Object)} is reflexive (an instant equals itself).</li>
     * </ul>
     */
    @Test(timeout = 4000)
    public void ofTaiSeconds_normalisesNegativeNanoAdjustment_andEqualsIsReflexive() throws Throwable {
        long taiSeconds = 3600000000000L;
        long negativeNanoAdjustment = -1194L;

        TaiInstant instant = TaiInstant.ofTaiSeconds(taiSeconds, negativeNanoAdjustment);

        // An instant is always equal to itself.
        assertTrue(instant.equals(instant));

        // -1194 nanos borrows one whole second: 3600000000000 - 1 seconds,
        // and 1_000_000_000 - 1194 = 999_998_806 nanos.
        assertEquals(3599999999999L, instant.getTaiSeconds());
        assertEquals(999998806, instant.getNano());
    }
}
