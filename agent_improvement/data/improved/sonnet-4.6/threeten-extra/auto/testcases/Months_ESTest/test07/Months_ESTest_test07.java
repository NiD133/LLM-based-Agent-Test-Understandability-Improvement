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
public class Months_ESTest_test07 extends Months_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_minusLargeValue_yieldsNegativeAmount_andAbsRestoresPositive() throws Throwable {
        // Start from Months.ONE (1 month) and subtract a large value to get a negative result
        Months oneMonth = Months.ONE;
        Months negativeMonths = oneMonth.minus(2635); // 1 - 2635 = -2634

        // abs() on a negative amount should return the positive equivalent
        Months absoluteMonths = negativeMonths.abs();

        assertEquals("minus(2635) applied to ONE should yield -2634", (-2634), negativeMonths.getAmount());
        assertEquals("abs() of -2634 should yield 2634", 2634, absoluteMonths.getAmount());
    }
}
