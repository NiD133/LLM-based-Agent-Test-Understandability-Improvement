package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.IsoEra;
import java.time.chrono.JapaneseEra;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;
import java.util.HashMap;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BritishCutoverChronology_ESTest_test12 extends BritishCutoverChronology_ESTest_scaffolding {

    private static final int INVALID_PROLEPTIC_YEAR = -1002;
    private static final int INVALID_DAY_OF_YEAR = -1002;

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();

        try {
            chronology.INSTANCE.dateYearDay(INVALID_PROLEPTIC_YEAR, INVALID_DAY_OF_YEAR);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            //
            // Invalid value for DayOfYear (valid values 1 - 365/366): -1002
            //
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
