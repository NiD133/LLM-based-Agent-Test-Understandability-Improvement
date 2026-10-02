package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test05 extends TaiInstant_ESTest_scaffolding {

    /**
     * Verifies that {@link TaiInstant#withTaiSeconds(long)} keeps the nano-of-second
     * unchanged while replacing the seconds, and that {@link TaiInstant#isBefore(TaiInstant)}
     * correctly orders two instants that differ only by their seconds field.
     */
    @Test(timeout = 4000)
    public void withTaiSecondsKeepsNanosAndIsBeforeComparesBySeconds() throws Throwable {
        // ofTaiSeconds normalises the negative nano adjustment (-5) by borrowing one
        // second, yielding seconds = -6 and nano-of-second = 999,999,995.
        TaiInstant earlier = TaiInstant.ofTaiSeconds(-5L, -5L);
        assertEquals(999999995, earlier.getNano());

        // Replacing only the seconds (-6 -> -5) leaves the normalised nanos untouched.
        TaiInstant later = earlier.withTaiSeconds(-5L);
        assertEquals(-5L, later.getTaiSeconds());
        assertEquals(999999995, later.getNano());

        // The original instant (seconds = -6) lies before the new one (seconds = -5).
        boolean earlierIsBeforeLater = earlier.isBefore(later);
        assertTrue(earlierIsBeforeLater);
    }
}
