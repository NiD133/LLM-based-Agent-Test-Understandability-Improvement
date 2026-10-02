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
public class Symmetry010Chronology_ESTest_test00 extends Symmetry010Chronology_ESTest_scaffolding {

    /**
     * Verifies that dateYearDay rejects day-of-year value 0, which lies below the valid range
     * of 1..364 (normal year) or 1..371 (leap year). The Symmetry454 chronology shares the same
     * era system (IsoEra) as Symmetry010, so an IsoEra obtained from a Symmetry454Date is a
     * valid era argument to Symmetry010Chronology.dateYearDay.
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // Arrange: obtain a valid IsoEra via the current Symmetry454 date
        Symmetry010Chronology chronology = Symmetry010Chronology.INSTANCE;
        Symmetry454Date today = Symmetry454Date.now();
        IsoEra currentEra = today.getEra();

        // Act + Assert: day-of-year 0 is below the minimum (1), so a DateTimeException must be thrown
        try {
            chronology.dateYearDay((Era) currentEra, 4, 0);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            //
            // Invalid value for DayOfYear (valid values 1 - 364/371): 0
            //
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
