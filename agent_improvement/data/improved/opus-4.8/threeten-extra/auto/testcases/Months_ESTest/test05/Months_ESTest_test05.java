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
     * Adding a non-zero {@code Months} amount to an {@code Instant} must fail,
     * because an {@code Instant} does not support the MONTHS unit.
     * {@code Months.ONE.addTo(instant)} delegates to {@code instant.plus(1, MONTHS)},
     * which rejects the unsupported unit.
     */
    @Test(timeout = 4000)
    public void addingMonthsToInstantThrowsUnsupportedUnit() throws Throwable {
        // Months.from(Period.ofWeeks(0)) exercises the conversion factory; ONE is the shared 1-month constant.
        Months oneMonth = Months.from(Period.ofWeeks(0)).ONE;
        Clock utcClock = MockClock.systemUTC();
        Instant instant = MockInstant.now(utcClock);

        try {
            oneMonth.addTo(instant);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException expected) {
            // Instant cannot be adjusted by the MONTHS unit.
            verifyException("java.time.Instant", expected);
        }
    }
}
