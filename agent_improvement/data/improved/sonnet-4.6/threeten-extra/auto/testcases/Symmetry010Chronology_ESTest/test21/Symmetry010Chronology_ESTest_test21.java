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

    /**
     * Verifies that a date created from a positive epoch day falls in the Common Era (CE).
     * Epoch day 702 is a date after the Unix epoch (1970-01-01), so it must be CE.
     */
    @Test(timeout = 4000)
    public void test21() throws Throwable {
        // Use the deprecated constructor as in the original; the INSTANCE singleton is accessed via it
        Symmetry010Chronology chronology = new Symmetry010Chronology();

        // Obtain the Symmetry010 date corresponding to epoch day 702
        Symmetry010Date dateFromEpochDay702 = chronology.INSTANCE.dateEpochDay(702L);

        // A positive epoch day lies in the Common Era
        assertEquals(IsoEra.CE, dateFromEpochDay702.getEra());
    }
}
