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
public class PaxChronology_ESTest_test01 extends PaxChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // Obtain a PaxDate and its associated chronology; epoch day 146096 maps to a valid Pax date
        PaxDate paxDate = PaxDate.ofEpochDay(146096L);
        PaxChronology paxChronology = paxDate.getChronology();

        // Verify that the chronology returns a non-null ValueRange for the MONTH_OF_YEAR field
        ValueRange monthOfYearRange = paxChronology.range(ChronoField.MONTH_OF_YEAR);
        assertNotNull(monthOfYearRange);
    }
}
