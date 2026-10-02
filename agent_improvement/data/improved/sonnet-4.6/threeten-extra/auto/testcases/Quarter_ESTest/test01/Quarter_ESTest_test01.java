package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.chrono.HijrahDate;
import java.time.chrono.JapaneseDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQuery;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockLocalDateTime;
import org.evosuite.runtime.mock.java.time.chrono.MockHijrahDate;
import org.evosuite.runtime.mock.java.time.chrono.MockJapaneseDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test01 extends Quarter_ESTest_scaffolding {

    // Verifies that adjusting a LocalDateTime into Q4 produces a different temporal value
    // when the original date is not already in Q4.
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Quarter q4 = Quarter.Q4;
        LocalDateTime now = MockLocalDateTime.now();
        Temporal adjustedToQ4 = q4.adjustInto(now);
        assertFalse(adjustedToQ4.equals((Object) now));
    }
}
