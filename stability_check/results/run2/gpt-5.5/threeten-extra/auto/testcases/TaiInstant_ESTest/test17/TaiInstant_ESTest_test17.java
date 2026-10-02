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

    private static final long ONE_DAY_OF_TAI_SECONDS = 86400L;
    private static final long NO_NANO_ADJUSTMENT = 0L;

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        TaiInstant oneDayAfterTaiEpoch = TaiInstant.ofTaiSeconds(
                ONE_DAY_OF_TAI_SECONDS,
                NO_NANO_ADJUSTMENT);

        oneDayAfterTaiEpoch.durationUntil(oneDayAfterTaiEpoch);

        assertEquals(ONE_DAY_OF_TAI_SECONDS, oneDayAfterTaiEpoch.getTaiSeconds());
        assertEquals(0, oneDayAfterTaiEpoch.getNano());
    }
}
