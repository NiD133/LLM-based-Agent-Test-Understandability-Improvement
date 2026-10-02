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
    public void test00() throws Throwable {
        final int initialNegativeWeeks = -1075;

        Weeks negativeWeeks = Weeks.of(initialNegativeWeeks);
        Weeks positiveWeeks = negativeWeeks.negated();
        Weeks negatedBackToOriginal = positiveWeeks.negated();

        boolean matchesOriginalNegativeWeeks = negatedBackToOriginal.equals(negativeWeeks);

        assertTrue(matchesOriginalNegativeWeeks);
        assertEquals(1075, positiveWeeks.getAmount());
        assertFalse(negatedBackToOriginal.equals((Object) positiveWeeks));
        assertEquals(initialNegativeWeeks, negatedBackToOriginal.getAmount());
        assertFalse(positiveWeeks.equals((Object) negativeWeeks));
    }
}
