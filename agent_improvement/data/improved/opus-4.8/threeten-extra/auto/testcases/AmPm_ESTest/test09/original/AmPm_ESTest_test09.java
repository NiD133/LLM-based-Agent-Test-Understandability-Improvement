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
public class AmPm_ESTest_test09 extends AmPm_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        LocalDate localDate0 = MockLocalDate.now();
        // Undeclared exception!
        try {
            AmPm.from(localDate0);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            //
            // Unable to obtain AmPm from TemporalAccessor: 2014-02-14 of type java.time.LocalDate
            //
            verifyException("org.threeten.extra.AmPm", e);
        }
    }
}
