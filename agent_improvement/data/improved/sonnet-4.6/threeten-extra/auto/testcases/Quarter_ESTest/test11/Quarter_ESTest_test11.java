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
public class Quarter_ESTest_test11 extends Quarter_ESTest_scaffolding {

    /**
     * Quarter only supports QUARTER_OF_YEAR; all ChronoField instances are unsupported.
     * Requesting SECOND_OF_DAY from a Quarter via ChronoField.getFrom() must throw
     * UnsupportedTemporalTypeException originating from Quarter itself.
     */
    @Test(timeout = 4000)
    public void test_getFrom_unsupportedChronoField_throwsUnsupportedTemporalTypeException() throws Throwable {
        Quarter q4 = Quarter.Q4;
        ChronoField secondOfDay = ChronoField.SECOND_OF_DAY;

        try {
            secondOfDay.getFrom(q4);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            verifyException("org.threeten.extra.Quarter", e);
        }
    }
}
