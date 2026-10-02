package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockLocalDate;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AmPm_ESTest_test15 extends AmPm_ESTest_scaffolding {

    // "上午" is the Traditional Chinese full-style display name for AM
    private static final String TRADITIONAL_CHINESE_AM = "上午";

    @Test(timeout = 4000)
    public void test_AM_getDisplayName_withFullStyle_inTraditionalChinese_returnsChineseAMText() throws Throwable {
        String displayName = AmPm.AM.getDisplayName(TextStyle.FULL, Locale.TRADITIONAL_CHINESE);
        assertEquals(TRADITIONAL_CHINESE_AM, displayName);
    }
}
