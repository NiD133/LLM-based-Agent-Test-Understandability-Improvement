package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test05 extends Seconds_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        // 1507 hours * 3600 seconds/hour = 5,425,200 seconds
        Seconds seconds1507Hours = Seconds.ofHours(1507);
        ZonedDateTime now = MockZonedDateTime.now();

        // Subtracting ZERO seconds leaves the temporal object unchanged (same reference)
        Temporal result = Seconds.ZERO.subtractFrom(now);
        assertSame(result, now);

        // Verify that the original Seconds amount is correctly computed from hours
        assertEquals(5425200, seconds1507Hours.getAmount());
    }
}
