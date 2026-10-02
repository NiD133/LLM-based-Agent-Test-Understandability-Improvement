package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.chrono.HijrahDate;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockLocalDate;
import org.evosuite.runtime.mock.java.time.chrono.MockHijrahDate;
import org.evosuite.runtime.mock.java.time.chrono.MockJapaneseDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test24 extends Weeks_ESTest_scaffolding {

    /**
     * Verifies that negating Weeks.ONE yields -1 week, and that taking the
     * absolute value of that negated result returns the original Weeks.ONE singleton.
     *
     * Weeks.ONE.negated() => -1 week (a new instance)
     * (-1 week).abs()     => 1 week, which is the cached Weeks.ONE singleton
     */
    @Test(timeout = 4000)
    public void test24() throws Throwable {
        Weeks oneWeek = Weeks.ONE;
        Weeks negatedOneWeek = oneWeek.negated();
        Weeks absoluteOfNegated = negatedOneWeek.abs();

        assertEquals("negated Weeks.ONE should have amount -1", (-1), negatedOneWeek.getAmount());
        assertSame("abs() of -1 week should return the Weeks.ONE singleton", absoluteOfNegated, oneWeek);
    }
}
