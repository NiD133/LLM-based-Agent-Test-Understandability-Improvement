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
public class TaiInstant_ESTest_test08 extends TaiInstant_ESTest_scaffolding {

    /**
     * Verifies that a negative nano adjustment wraps into the previous second,
     * and that isAfter() correctly returns false when the instant is earlier
     * than the reference.
     *
     * TaiInstant.ofTaiSeconds(883, -784) normalises the negative nano adjustment:
     *   seconds = 883 + floorDiv(-784, 1_000_000_000) = 883 + (-1) = 882
     *   nanos   = floorMod(-784, 1_000_000_000)       = 999_999_216
     */
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        // Reference instant at exactly one day (86400 s) into the TAI epoch
        TaiInstant oneDayInstant = TaiInstant.ofTaiSeconds(86400L, 0L);

        // Instant created with a negative nano adjustment; the factory normalises it
        // so that nanos is always in [0, 999_999_999].
        // -784 ns wraps: seconds becomes 882, nanos becomes 999_999_216.
        TaiInstant beforeOneDayInstant = TaiInstant.ofTaiSeconds(883L, (-784L));

        // 882 seconds is far earlier than 86400 seconds, so isAfter must be false
        boolean isBeforeOneDayInstant = beforeOneDayInstant.isAfter(oneDayInstant);

        // Confirm the normalised nano-of-second after the negative adjustment
        assertEquals(999999216, beforeOneDayInstant.getNano());
        // Confirm the second was decremented by 1 due to the negative nano adjustment
        assertEquals(882L, beforeOneDayInstant.getTaiSeconds());
        assertFalse(isBeforeOneDayInstant);
    }
}
