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
public class Seconds_ESTest_test30 extends Seconds_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test30() throws Throwable {
        long negativeDays = -1032L;
        int expectedSeconds = -89164800;

        Seconds zeroSeconds = Seconds.ZERO;
        Duration durationOfNegativeDays = Duration.ofDays(negativeDays);

        Seconds convertedSeconds = zeroSeconds.ZERO.plus((TemporalAmount) durationOfNegativeDays);

        assertEquals(expectedSeconds, convertedSeconds.getAmount());
    }
}
