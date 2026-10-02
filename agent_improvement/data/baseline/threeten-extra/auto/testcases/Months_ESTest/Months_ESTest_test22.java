package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import java.time.Clock;
import java.time.Instant;
import java.time.Period;
import java.time.chrono.HijrahDate;
import java.time.chrono.ThaiBuddhistDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.evosuite.runtime.mock.java.time.chrono.MockHijrahDate;
import org.evosuite.runtime.mock.java.time.chrono.MockThaiBuddhistDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test22 extends Months_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test22() throws Throwable {
        Months months0 = Months.ofYears(83);
        Period period0 = Period.from(months0);
        Months months1 = Months.from(period0);
        boolean boolean0 = months0.equals(months1);
        assertEquals(996, months0.getAmount());
        assertTrue(boolean0);
    }
}
