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
public class Seconds_ESTest_test01 extends Seconds_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Seconds seconds0 = Seconds.ofHours(396);
        Duration duration0 = Duration.ofSeconds((long) 396);
        Seconds seconds1 = seconds0.minus((TemporalAmount) duration0);
        boolean boolean0 = seconds0.equals(seconds1);
        assertEquals(1425204, seconds1.getAmount());
        assertFalse(seconds1.equals((Object) seconds0));
        assertFalse(boolean0);
        assertEquals(1425600, seconds0.getAmount());
    }
}
