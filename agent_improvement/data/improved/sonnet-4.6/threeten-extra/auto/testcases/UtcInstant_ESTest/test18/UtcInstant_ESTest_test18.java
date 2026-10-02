package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UtcInstant_ESTest_test18 extends UtcInstant_ESTest_scaffolding {

    // MJD 36203 is the Modified Julian Day for the date represented by TAI seconds = -745
    private static final long EXPECTED_MJD = 36203L;

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        // Construct a TAI instant at -745 TAI seconds with a -1000 nanosecond sub-second adjustment
        TaiInstant taiInstant = TaiInstant.ofTaiSeconds(-745L, -1000L);

        // Convert the TAI instant to a UTC instant; both scales map to the same MJD here
        UtcInstant originalUtcInstant = UtcInstant.of(taiInstant);

        // A negative duration of 1000 minutes shifts the instant backward by ~16.7 hours
        Duration negativeDurationOf1000Minutes = Duration.ofMinutes(-1000L);

        // Adding the negative duration moves the instant back, but it remains within the same MJD
        UtcInstant shiftedUtcInstant = originalUtcInstant.plus(negativeDurationOf1000Minutes);

        // Both instants must fall on the same Modified Julian Day
        assertEquals(EXPECTED_MJD, originalUtcInstant.getModifiedJulianDay());
        assertEquals(EXPECTED_MJD, shiftedUtcInstant.getModifiedJulianDay());
    }
}
