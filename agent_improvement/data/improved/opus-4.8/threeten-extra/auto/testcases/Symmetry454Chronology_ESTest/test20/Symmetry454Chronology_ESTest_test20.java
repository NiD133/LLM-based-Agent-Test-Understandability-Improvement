package org.threeten.extra.chrono;

import static org.junit.Assert.assertNotNull;

import java.time.OffsetDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.temporal.TemporalAccessor;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry454Chronology_ESTest_test20 extends Symmetry454Chronology_ESTest_scaffolding {

    /**
     * Verifies that {@link Symmetry454Chronology#zonedDateTime(TemporalAccessor)}
     * builds a (non-null) zoned date-time when given an {@link OffsetDateTime},
     * which carries date, time and zone-offset information.
     */
    @Test(timeout = 4000)
    public void zonedDateTimeFromOffsetDateTimeReturnsNonNullResult() throws Throwable {
        Symmetry454Chronology chronology = new Symmetry454Chronology();
        OffsetDateTime currentOffsetDateTime = MockOffsetDateTime.now();

        ChronoZonedDateTime<Symmetry454Date> zonedDateTime =
                chronology.zonedDateTime((TemporalAccessor) currentOffsetDateTime);

        assertNotNull(zonedDateTime);
    }
}
