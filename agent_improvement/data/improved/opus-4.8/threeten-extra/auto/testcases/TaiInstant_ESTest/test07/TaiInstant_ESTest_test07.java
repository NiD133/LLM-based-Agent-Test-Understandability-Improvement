package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test07 extends TaiInstant_ESTest_scaffolding {

    /**
     * Verifies that an instant with a non-zero nanosecond fraction is reported
     * as being after the same instant once its fraction is cleared to zero.
     */
    @Test(timeout = 4000)
    public void clearingNanosMakesInstantEarlier() throws Throwable {
        // A negative nano adjustment is normalised: the seconds count is rolled
        // back by one and the nanoseconds become 1_000_000_000 - 1194 = 999998806.
        TaiInstant instantWithNanos = TaiInstant.ofTaiSeconds(-3113L, -1194L);
        assertEquals(-3114L, instantWithNanos.getTaiSeconds());
        assertEquals(999998806, instantWithNanos.getNano());

        // Clearing the nano fraction keeps the same second but resets nanos to zero.
        TaiInstant instantOnSecondBoundary = instantWithNanos.withNano(0);
        assertEquals(-3114L, instantOnSecondBoundary.getTaiSeconds());
        assertEquals(0, instantOnSecondBoundary.getNano());

        // Same seconds, but the original has a larger nano fraction, so it is later.
        boolean isOriginalAfterCleared = instantWithNanos.isAfter(instantOnSecondBoundary);
        assertTrue(isOriginalAfterCleared);
    }
}
