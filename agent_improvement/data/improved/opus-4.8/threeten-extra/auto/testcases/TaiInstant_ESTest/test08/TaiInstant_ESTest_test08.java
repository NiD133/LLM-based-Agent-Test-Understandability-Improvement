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
     * Verifies that {@link TaiInstant#isAfter(TaiInstant)} returns false when the
     * receiver lies earlier on the time-line than the argument.
     *
     * <p>The earlier instant is built with a negative nanosecond adjustment, which
     * {@link TaiInstant#ofTaiSeconds(long, long)} normalises by borrowing one second:
     * 883 seconds with -784 nanos becomes 882 seconds and 999,999,216 nanos
     * (1,000,000,000 - 784 = 999,999,216). It therefore remains well before the
     * later instant at 86,400 seconds.
     */
    @Test(timeout = 4000)
    public void isAfterReturnsFalseWhenInstantIsEarlier() throws Throwable {
        TaiInstant laterInstant = TaiInstant.ofTaiSeconds(86400L, 0L);
        TaiInstant earlierInstant = TaiInstant.ofTaiSeconds(883L, -784L);

        boolean earlierIsAfterLater = earlierInstant.isAfter(laterInstant);

        // Negative nano adjustment borrows a second during normalisation.
        assertEquals(882L, earlierInstant.getTaiSeconds());
        assertEquals(999999216, earlierInstant.getNano());
        assertFalse(earlierIsAfterLater);
    }
}
