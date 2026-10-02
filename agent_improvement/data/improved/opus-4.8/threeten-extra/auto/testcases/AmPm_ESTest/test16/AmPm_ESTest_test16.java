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
public class AmPm_ESTest_test16 extends AmPm_ESTest_scaffolding {

    /**
     * Adjusting a ZonedDateTime with AM forces its AMPM_OF_DAY field to the
     * morning value. The "now" produced by the mock clock falls in the
     * afternoon, so applying AM yields a different ZonedDateTime than the input.
     */
    @Test(timeout = 4000)
    public void adjustIntoWithAmReturnsDifferentTemporal() throws Throwable {
        AmPm am = AmPm.AM;
        ZonedDateTime originalDateTime = MockZonedDateTime.now();

        Temporal adjustedDateTime = am.adjustInto(originalDateTime);

        assertFalse(adjustedDateTime.equals((Object) originalDateTime));
    }
}
