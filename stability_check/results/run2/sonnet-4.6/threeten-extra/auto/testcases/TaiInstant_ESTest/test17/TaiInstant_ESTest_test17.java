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
public class TaiInstant_ESTest_test17 extends TaiInstant_ESTest_scaffolding {

    // 86400 seconds = 24 * 60 * 60 (one full day in seconds)
    private static final long SECONDS_IN_ONE_DAY = 86400L;

    @Test(timeout = 4000)
    public void test_durationUntilSelf_returnsZeroAndInstantUnchanged() throws Throwable {
        // Create a TAI instant at exactly one day after the TAI epoch with no nanosecond offset
        TaiInstant oneDayAfterEpoch = TaiInstant.ofTaiSeconds(SECONDS_IN_ONE_DAY, 0L);

        // Computing the duration from an instant to itself should yield zero duration
        oneDayAfterEpoch.durationUntil(oneDayAfterEpoch);

        // The instant must remain unchanged after calling durationUntil
        assertEquals(SECONDS_IN_ONE_DAY, oneDayAfterEpoch.getTaiSeconds());
        assertEquals(0, oneDayAfterEpoch.getNano());
    }
}
