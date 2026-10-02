package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.IsoEra;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.ValueRange;
import java.util.List;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockClock;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry010Chronology_ESTest_test21 extends Symmetry010Chronology_ESTest_scaffolding {

    // Epoch day 702 is a positive day after the Unix epoch (1970-01-01), placing it in the Common Era (CE)
    private static final long EPOCH_DAY_IN_CE = 702L;

    @Test(timeout = 4000)
    public void test21_dateFromPositiveEpochDay_hasCommonEra() throws Throwable {
        Symmetry010Date dateFromEpochDay = Symmetry010Chronology.INSTANCE.dateEpochDay(EPOCH_DAY_IN_CE);
        assertEquals(IsoEra.CE, dateFromEpochDay.getEra());
    }
}
