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
public class Weeks_ESTest_test00 extends Weeks_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_negatedTwiceRestoresOriginalAndEqualityIsValueBased() throws Throwable {
        Weeks negativeWeeks = Weeks.of(-1075);
        Weeks positiveWeeks = negativeWeeks.negated();
        Weeks doubleNegatedWeeks = positiveWeeks.negated();

        // Double negation returns to the original value
        assertTrue(doubleNegatedWeeks.equals(negativeWeeks));
        assertEquals(-1075, doubleNegatedWeeks.getAmount());

        // Single negation has the opposite sign
        assertEquals(1075, positiveWeeks.getAmount());

        // Weeks with different values are not equal
        assertFalse(doubleNegatedWeeks.equals((Object) positiveWeeks));
        assertFalse(positiveWeeks.equals((Object) negativeWeeks));
    }
}
