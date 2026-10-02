package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.IsoChronology;
import java.time.chrono.ThaiBuddhistDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.chrono.MockThaiBuddhistDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test13 extends Years_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_dividedByLargeDivisorYieldsZero_thenMinusZeroLeavesOriginal() throws Throwable {
        // Dividing 1 year by 498 truncates to 0 via integer division
        Years oneYear = Years.ONE;
        Years zeroYears = oneYear.dividedBy(498);

        // Subtracting 0 years from 1 year should leave the amount unchanged
        Years result = Years.ONE.minus((TemporalAmount) zeroYears);

        assertEquals(1, result.getAmount());
        assertEquals(0, zeroYears.getAmount());
    }
}
