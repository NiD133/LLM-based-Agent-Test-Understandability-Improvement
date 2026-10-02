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
public class TaiInstant_ESTest_test05 extends TaiInstant_ESTest_scaffolding {

    // ofTaiSeconds(-5, -5) normalizes the negative nano adjustment:
    //   floorMod(-5, 1_000_000_000) = 999_999_995  (stored nanos)
    //   floorDiv(-5, 1_000_000_000) = -1            (added to seconds: -5 + -1 = -6)
    // So the resulting instant is at taiSeconds=-6, nanos=999_999_995.
    private static final int NORMALIZED_NANOS = 999_999_995;

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        // Instant at taiSeconds=-6, nanos=999_999_995 (after normalization of -5 ns)
        TaiInstant earlierInstant = TaiInstant.ofTaiSeconds((-5), (-5));

        // Replace only the seconds field; nanos (999_999_995) are preserved.
        // Result: taiSeconds=-5, nanos=999_999_995 — one second later on the timeline.
        TaiInstant laterInstant = earlierInstant.withTaiSeconds((-5));

        // earlierInstant (seconds=-6) precedes laterInstant (seconds=-5)
        boolean earlierIsBeforeLater = earlierInstant.isBefore(laterInstant);

        assertEquals(NORMALIZED_NANOS, laterInstant.getNano());
        assertEquals((-5L), laterInstant.getTaiSeconds());
        assertEquals(NORMALIZED_NANOS, earlierInstant.getNano());
        assertTrue(earlierIsBeforeLater);
    }
}
