package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.chrono.MinguoDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.evosuite.runtime.mock.java.time.chrono.MockMinguoDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test04 extends Days_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        Instant instant0 = MockInstant.ofEpochSecond((-2086L), (-2086L));
        Days days0 = Days.between(instant0, instant0);
        Duration duration0 = Duration.ofDays((-2086L));
        Days days1 = days0.minus((TemporalAmount) duration0);
        Temporal temporal0 = days1.subtractFrom(instant0);
        assertEquals(2086, days1.getAmount());
        assertNotSame(temporal0, instant0);
        assertEquals(0, days0.getAmount());
    }
}
