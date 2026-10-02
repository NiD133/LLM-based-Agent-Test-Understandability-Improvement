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
public class TaiInstant_ESTest_test12 extends TaiInstant_ESTest_scaffolding {

    /**
     * Subtracting a sub-second duration from the start of a UTC day should wrap the
     * nano-of-day backwards into the previous day, landing just over 3 seconds before
     * the end of that 86400-second day.
     */
    @Test(timeout = 4000)
    public void plusNegativeDurationWrapsNanoOfDayIntoPreviousDay() throws Throwable {
        // Start exactly at midnight (nano-of-day 0) of a given Modified Julian Day.
        UtcInstant startOfDay = UtcInstant.ofModifiedJulianDay(-1194L, 0);

        // Move 3113 milliseconds earlier, i.e. before midnight.
        Duration backBy3113Millis = Duration.ofMillis(-3113L);
        UtcInstant shifted = startOfDay.plus(backBy3113Millis);

        // 3113 ms before midnight = 86400s - 3.113s = 86396.887s, expressed in nanos.
        assertEquals(86396887000000L, shifted.getNanoOfDay());
    }
}
