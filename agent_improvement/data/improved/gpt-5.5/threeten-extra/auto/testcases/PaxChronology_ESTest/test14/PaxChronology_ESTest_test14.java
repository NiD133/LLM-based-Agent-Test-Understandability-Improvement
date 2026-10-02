package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.ThaiBuddhistEra;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.HashMap;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test14 extends PaxChronology_ESTest_scaffolding {

    private static final int INVALID_PROLEPTIC_YEAR = -1093;
    private static final int INVALID_MONTH = -1093;
    private static final int INVALID_DAY_OF_MONTH = 0;

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        PaxChronology chronology = new PaxChronology();

        try {
            chronology.date(INVALID_PROLEPTIC_YEAR, INVALID_MONTH, INVALID_DAY_OF_MONTH);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException exception) {
            verifyException("java.time.temporal.ValueRange", exception);
        }
    }
}
