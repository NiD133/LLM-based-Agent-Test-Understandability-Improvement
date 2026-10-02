package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test00 extends TaiInstant_ESTest_scaffolding {

    /**
     * Verifies that {@link TaiInstant#ofTaiSeconds(long, long)} normalises a
     * negative nanosecond adjustment into the valid 0..999,999,999 range by
     * borrowing one second, and that two instances built from identical inputs
     * are equal.
     */
    @Test(timeout = 4000)
    public void ofTaiSecondsNormalisesNegativeNanosAndComparesEqual() throws Throwable {
        // A nano adjustment of -3113 is below zero, so the factory borrows one
        // second: seconds become -3113 - 1 = -3114, and the nanos wrap to
        // 1_000_000_000 - 3113 = 999_996_887.
        TaiInstant instant = TaiInstant.ofTaiSeconds(-3113L, -3113L);
        TaiInstant sameInstant = TaiInstant.ofTaiSeconds(-3113L, -3113L);

        assertEquals(-3114L, sameInstant.getTaiSeconds());
        assertEquals(999996887, sameInstant.getNano());
        assertTrue(instant.equals(sameInstant));
    }
}
