package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test07 extends TaiInstant_ESTest_scaffolding {

    /**
     * Verifies that ofTaiSeconds normalizes a negative nanosecond adjustment by borrowing
     * from the seconds field, and that withNano(0) zeroes the nanosecond part while
     * preserving the (already normalized) seconds value. Also confirms that the original
     * instant (with non-zero nanos) is correctly identified as after the zeroed copy.
     *
     * Input: ofTaiSeconds(-3113, -1194)
     *   -> floorDiv(-1194, 1_000_000_000) = -1  =>  seconds = -3113 + (-1) = -3114
     *   -> floorMod(-1194, 1_000_000_000) = 999_998_806  =>  nanos = 999_998_806
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        // Create an instant whose negative nano adjustment is normalized into the seconds field.
        TaiInstant instantWithNegativeNanoAdjustment = TaiInstant.ofTaiSeconds((-3113L), (-1194L));

        // Zero out the nanosecond component; seconds remain unchanged after prior normalization.
        TaiInstant instantWithZeroedNanos = instantWithNegativeNanoAdjustment.withNano(0);

        // The original instant has a large positive nano offset, so it is after the zeroed copy.
        boolean originalIsAfterZeroed = instantWithNegativeNanoAdjustment.isAfter(instantWithZeroedNanos);

        // Both instants share the same normalized seconds value (-3114).
        assertEquals((-3114L), instantWithZeroedNanos.getTaiSeconds());
        assertEquals(0, instantWithZeroedNanos.getNano());
        assertEquals((-3114L), instantWithNegativeNanoAdjustment.getTaiSeconds());

        // The original instant is after the zeroed copy because 999_998_806 ns > 0 ns.
        assertTrue(originalIsAfterZeroed);
        assertEquals(999998806, instantWithNegativeNanoAdjustment.getNano());
    }
}
