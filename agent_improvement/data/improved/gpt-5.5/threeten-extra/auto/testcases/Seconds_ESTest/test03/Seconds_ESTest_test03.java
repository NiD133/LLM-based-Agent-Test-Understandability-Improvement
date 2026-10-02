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
public class Seconds_ESTest_test03 extends Seconds_ESTest_scaffolding {

    private static final int NEGATIVE_MINUTES = -19;
    private static final int EXPECTED_SECONDS = -1140;

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        CharBuffer emptyBuffer = CharBuffer.allocate(0);
        Seconds secondsFromMinutes = Seconds.ofMinutes(NEGATIVE_MINUTES);

        boolean equalsNonSecondsType = secondsFromMinutes.equals(emptyBuffer);

        assertFalse(equalsNonSecondsType);
        assertEquals(EXPECTED_SECONDS, secondsFromMinutes.getAmount());
    }
}
