package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.Instant;
import java.time.Period;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test05 extends Months_ESTest_scaffolding {

    /**
     * Verifies that adding Months.ONE to an Instant throws UnsupportedTemporalTypeException,
     * because Instant does not support month-based temporal units.
     */
    @Test(timeout = 4000)
    public void test_addMonthsToInstant_throwsUnsupportedTemporalTypeException() throws Throwable {
        // Obtain a zero-month Months instance derived from a zero-week Period
        Period zeroPeriod = Period.ofWeeks(0);
        Months zeroMonths = Months.from(zeroPeriod);

        // Obtain a current Instant from a UTC clock
        Clock utcClock = MockClock.systemUTC();
        Instant now = MockInstant.now(utcClock);

        // Instant does not support the MONTHS unit, so addTo must throw
        try {
            zeroMonths.ONE.addTo(now);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            verifyException("java.time.Instant", e);
        }
    }
}
