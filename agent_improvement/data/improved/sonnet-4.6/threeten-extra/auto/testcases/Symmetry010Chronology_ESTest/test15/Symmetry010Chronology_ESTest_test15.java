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
public class Symmetry010Chronology_ESTest_test15 extends Symmetry010Chronology_ESTest_scaffolding {

    /**
     * Dates created from epoch days greater than zero fall in the CE (Common Era),
     * since epoch day 0 corresponds to 1970-01-01 which is well within CE.
     */
    @Test(timeout = 4000)
    public void test_getEra_returnsCommonEra_forPositiveEpochDay() throws Throwable {
        // Epoch day 3 corresponds to January 4, 1970 — a date in the Common Era
        Symmetry010Date dateInCommonEra = Symmetry010Date.ofEpochDay(3);

        assertEquals(IsoEra.CE, dateInCommonEra.getEra());
    }
}
