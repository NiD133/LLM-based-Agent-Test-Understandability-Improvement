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
public class AmPm_ESTest_test14 extends AmPm_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_amPm_doesNotSupport_microOfSecondField() throws Throwable {
        // AmPm.of(0) returns AM; AmPm only supports AMPM_OF_DAY, not time-of-day fields
        AmPm am = AmPm.of(0);
        assertEquals(AmPm.AM, am);

        boolean isMicroOfSecondSupported = am.isSupported(ChronoField.MICRO_OF_SECOND);
        assertFalse(isMicroOfSecondSupported);
    }
}
