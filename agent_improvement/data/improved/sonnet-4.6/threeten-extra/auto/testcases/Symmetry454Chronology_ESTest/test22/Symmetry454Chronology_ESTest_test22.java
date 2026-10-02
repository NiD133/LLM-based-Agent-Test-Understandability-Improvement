package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.IsoEra;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.ValueRange;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry454Chronology_ESTest_test22 extends Symmetry454Chronology_ESTest_scaffolding {

    // Epoch day 1 corresponds to January 2, 1970 (one day after the Unix epoch),
    // which falls in the Common Era (CE).
    private static final long EPOCH_DAY_ONE = 1L;

    @Test(timeout = 4000)
    public void test22_dateEpochDayOneIsInCommonEra() throws Throwable {
        Symmetry454Chronology chronology = Symmetry454Chronology.INSTANCE;

        Symmetry454Date dateForEpochDayOne = chronology.dateEpochDay(EPOCH_DAY_ONE);

        assertEquals(IsoEra.CE, dateForEpochDayOne.getEra());
    }
}
