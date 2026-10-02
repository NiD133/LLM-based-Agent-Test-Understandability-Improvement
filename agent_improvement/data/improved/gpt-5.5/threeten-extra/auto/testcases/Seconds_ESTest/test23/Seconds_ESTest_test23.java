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
public class Seconds_ESTest_test23 extends Seconds_ESTest_scaffolding {

    private static final int ONE_MINUTE_BEFORE_ZERO = -1;
    private static final int EXPECTED_SECONDS_IN_NEGATIVE_MINUTE = -60;
    private static final int EXPECTED_SECONDS_AFTER_SUBTRACTING_ITSELF = 0;

    @Test(timeout = 4000)
    public void test23() throws Throwable {
        Seconds negativeOneMinute = Seconds.ofMinutes(ONE_MINUTE_BEFORE_ZERO);
        Seconds zeroSeconds = negativeOneMinute.minus((TemporalAmount) negativeOneMinute);

        assertEquals(EXPECTED_SECONDS_IN_NEGATIVE_MINUTE, negativeOneMinute.getAmount());
        assertEquals(EXPECTED_SECONDS_AFTER_SUBTRACTING_ITSELF, zeroSeconds.getAmount());
    }
}
