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
public class UtcInstant_ESTest_test12 extends UtcInstant_ESTest_scaffolding {

    /**
     * An instant is never "after" itself, and converting the epoch-second value 3
     * into a UtcInstant yields the expected Modified Julian Day and nano-of-day.
     */
    @Test(timeout = 4000)
    public void isAfterSelfIsFalseAndConversionFieldsAreCorrect() throws Throwable {
        // 3 seconds after the Unix epoch (1970-01-01T00:00:03Z).
        Instant threeSecondsAfterEpoch = MockInstant.ofEpochSecond(3L);
        UtcInstant utcInstant = UtcInstant.of(threeSecondsAfterEpoch);

        boolean isAfterItself = utcInstant.isAfter(utcInstant);

        // An instant can never be after itself.
        assertFalse(isAfterItself);
        // 3 seconds into the day, expressed in nanoseconds.
        assertEquals(3_000_000_000L, utcInstant.getNanoOfDay());
        // MJD 40587 is the Modified Julian Day for the Unix epoch (1970-01-01).
        assertEquals(40587L, utcInstant.getModifiedJulianDay());
    }
}
