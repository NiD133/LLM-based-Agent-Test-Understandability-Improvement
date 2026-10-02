package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Instant;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UtcInstant_ESTest_test05 extends UtcInstant_ESTest_scaffolding {

    // TAI seconds value far in the future, used to construct a UtcInstant distinct from "now"
    private static final long FAR_FUTURE_TAI_SECONDS = 36791000000652L;
    private static final long FAR_FUTURE_TAI_NANOS   = 36791000000652L;

    // Expected UTC representation of the mocked "current" Instant (EvoSuite fixes the clock)
    private static final long EXPECTED_MJD_OF_MOCKED_NOW       = 56702L;
    private static final long EXPECTED_NANO_OF_DAY_OF_MOCKED_NOW = 73281320000000L;

    /**
     * Verifies that a UtcInstant created from the mocked current Instant has the
     * expected Modified Julian Day and nano-of-day, and is not equal to a UtcInstant
     * derived from a TAI instant far in the future.
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        // Obtain the mocked "now" — EvoSuite fixes the clock so values are deterministic
        Instant mockedNow = MockInstant.now();

        // Build a UtcInstant from a TAI instant far in the future (distinct from "now")
        TaiInstant farFutureTai = TaiInstant.ofTaiSeconds(FAR_FUTURE_TAI_SECONDS, FAR_FUTURE_TAI_NANOS);
        UtcInstant farFutureUtc = farFutureTai.toUtcInstant();

        // Build a UtcInstant from the mocked current time
        UtcInstant utcFromMockedNow = UtcInstant.of(mockedNow);

        // The two instants represent different points in time
        boolean areEqual = utcFromMockedNow.equals(farFutureUtc);
        assertFalse("UtcInstant from mocked now should differ from far-future TAI instant", areEqual);

        // Confirm the mocked-now instant has the expected UTC date (MJD) and time (nano-of-day)
        assertEquals("Modified Julian Day of mocked now", EXPECTED_MJD_OF_MOCKED_NOW, utcFromMockedNow.getModifiedJulianDay());
        assertEquals("Nano-of-day of mocked now", EXPECTED_NANO_OF_DAY_OF_MOCKED_NOW, utcFromMockedNow.getNanoOfDay());
    }
}
