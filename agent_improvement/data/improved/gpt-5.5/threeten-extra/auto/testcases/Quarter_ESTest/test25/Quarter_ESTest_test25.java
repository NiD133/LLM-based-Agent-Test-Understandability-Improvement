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
public class Quarter_ESTest_test25 extends Quarter_ESTest_scaffolding {

    private static final int FOURTH_QUARTER_VALUE = 4;
    private static final Locale JAPANESE_LOCALE = Locale.JAPAN;
    private static final TextStyle SHORT_DISPLAY_STYLE = TextStyle.SHORT;

    @Test(timeout = 4000)
    public void test25() throws Throwable {
        Quarter fourthQuarter = Quarter.of(FOURTH_QUARTER_VALUE);

        String displayName = fourthQuarter.getDisplayName(SHORT_DISPLAY_STYLE, JAPANESE_LOCALE);

        assertEquals("Q4", displayName);
    }
}
