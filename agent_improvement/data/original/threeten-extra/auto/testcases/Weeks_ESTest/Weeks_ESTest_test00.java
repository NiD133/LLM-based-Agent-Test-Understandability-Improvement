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
        Weeks weeks0 = Weeks.of((-1075));
        Weeks weeks1 = weeks0.negated();
        Weeks weeks2 = weeks1.negated();
        boolean boolean0 = weeks2.equals(weeks0);
        assertTrue(boolean0);
        assertEquals(1075, weeks1.getAmount());
        assertFalse(weeks2.equals((Object) weeks1));
        assertEquals((-1075), weeks2.getAmount());
        assertFalse(weeks1.equals((Object) weeks0));
    }
}
