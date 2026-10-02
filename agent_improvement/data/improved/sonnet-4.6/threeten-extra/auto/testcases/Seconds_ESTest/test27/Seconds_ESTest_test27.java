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
public class Seconds_ESTest_test27 extends Seconds_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_ofMinutes_negativeInput_toDurationDoesNotThrow_andAmountEqualsMinutesInSeconds() throws Throwable {
        // -19 minutes expressed as seconds: -19 * 60 = -1140
        Seconds negativeMinutes = Seconds.ofMinutes(-19);

        // Verify that converting to Duration does not throw an exception
        Duration duration = negativeMinutes.toDuration();

        // The raw second count should equal -19 * 60
        assertEquals(-1140, negativeMinutes.getAmount());
    }
}
