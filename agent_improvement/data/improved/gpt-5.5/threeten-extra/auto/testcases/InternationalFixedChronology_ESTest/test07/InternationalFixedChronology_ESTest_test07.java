package org.threeten.extra.chrono;

import static org.evosuite.runtime.EvoAssertions.*;
import static org.junit.Assert.*;

import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test07 extends InternationalFixedChronology_ESTest_scaffolding {

    private static final long INVALID_DAY_OF_YEAR = 1820L;

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        ZoneOffset utcZone = ZoneOffset.UTC;
        InternationalFixedDate utcToday = InternationalFixedDate.now((ZoneId) utcZone);
        ChronoField dayOfYearField = ChronoField.DAY_OF_YEAR;

        try {
            utcToday.with((TemporalField) dayOfYearField, INVALID_DAY_OF_YEAR);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
