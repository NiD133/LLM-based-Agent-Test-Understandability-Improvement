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

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        Locale traditionalChinese = Locale.TRADITIONAL_CHINESE;
        AmPm morningHalfOfDay = AmPm.AM;
        TextStyle fullTextStyle = TextStyle.FULL;

        String displayName = morningHalfOfDay.getDisplayName(fullTextStyle, traditionalChinese);

        assertEquals("\u4E0A\u5348", displayName);
    }
}
