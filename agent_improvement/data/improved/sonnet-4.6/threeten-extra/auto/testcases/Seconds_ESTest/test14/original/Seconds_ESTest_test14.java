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
public class Seconds_ESTest_test14 extends Seconds_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        Seconds seconds0 = Seconds.ofMinutes((-19));
        Seconds seconds1 = seconds0.dividedBy(9);
        Seconds seconds2 = seconds1.minus((TemporalAmount) seconds0);
        boolean boolean0 = seconds2.isPositive();
        assertEquals(1014, seconds2.getAmount());
        assertTrue(boolean0);
        assertFalse(seconds1.isPositive());
        assertEquals((-1140), seconds0.getAmount());
    }
}
