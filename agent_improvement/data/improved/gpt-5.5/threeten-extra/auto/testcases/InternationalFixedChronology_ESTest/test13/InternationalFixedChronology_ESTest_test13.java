package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.JapaneseEra;
import java.time.chrono.ThaiBuddhistEra;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InternationalFixedChronology_ESTest_test13 extends InternationalFixedChronology_ESTest_scaffolding {

    private static final int YEAR = 7;
    private static final int DAY_OF_YEAR = 7;
    private static final long EXPECTED_EPOCH_DAY = -716965L;
    private static final int EXPECTED_STANDARD_YEAR_LENGTH = 365;

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        InternationalFixedChronology chronology = new InternationalFixedChronology();
        InternationalFixedDate originalDate = chronology.dateYearDay(YEAR, DAY_OF_YEAR);
        EthiopicDate ethiopicAdjuster = EthiopicDate.from(originalDate);

        InternationalFixedDate adjustedDate = originalDate.with((TemporalAdjuster) ethiopicAdjuster);

        assertEquals(EXPECTED_EPOCH_DAY, adjustedDate.toEpochDay());
        assertEquals(EXPECTED_STANDARD_YEAR_LENGTH, adjustedDate.lengthOfYear());
    }
}
