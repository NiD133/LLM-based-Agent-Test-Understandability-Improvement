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
public class Months_ESTest_test00 extends Months_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        int startingMonths = -1428;
        int monthsToSubtract = -2443;
        int expectedMonthsAfterSubtraction = 1015;

        Months originalAmount = Months.of(startingMonths);
        Months adjustedAmount = originalAmount.minus(monthsToSubtract);

        boolean adjustedEqualsOriginal = adjustedAmount.equals(originalAmount);

        assertFalse(originalAmount.equals((Object) adjustedAmount));
        assertEquals(expectedMonthsAfterSubtraction, adjustedAmount.getAmount());
        assertFalse(adjustedEqualsOriginal);
    }
}
