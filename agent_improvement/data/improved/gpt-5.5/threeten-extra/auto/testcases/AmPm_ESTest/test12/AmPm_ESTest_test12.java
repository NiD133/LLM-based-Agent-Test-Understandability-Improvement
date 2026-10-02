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
public class AmPm_ESTest_test12 extends AmPm_ESTest_scaffolding {

    private static final int INVALID_AM_PM_VALUE = 36;
    private static final String AM_PM_CLASS_NAME = "org.threeten.extra.AmPm";

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        try {
            AmPm.of(INVALID_AM_PM_VALUE);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            verifyException(AM_PM_CLASS_NAME, e);
        }
    }
}
