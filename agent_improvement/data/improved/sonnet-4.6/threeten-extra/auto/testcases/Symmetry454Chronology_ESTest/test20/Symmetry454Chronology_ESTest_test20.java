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
public class Symmetry454Chronology_ESTest_test20 extends Symmetry454Chronology_ESTest_scaffolding {

    /**
     * Verifies that converting an ISO OffsetDateTime to a Symmetry454 zoned date-time
     * produces a non-null ChronoZonedDateTime result.
     */
    @Test(timeout = 4000)
    public void test20() throws Throwable {
        Symmetry454Chronology chronology = new Symmetry454Chronology();
        OffsetDateTime currentIsoOffsetDateTime = MockOffsetDateTime.now();
        ChronoZonedDateTime<Symmetry454Date> sym454ZonedDateTime =
                chronology.zonedDateTime((TemporalAccessor) currentIsoOffsetDateTime);
        assertNotNull("Expected zonedDateTime() to return a non-null Symmetry454 zoned date-time", sym454ZonedDateTime);
    }
}
