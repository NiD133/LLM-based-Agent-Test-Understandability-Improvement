package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.OffsetDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.temporal.TemporalAccessor;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry010Chronology_ESTest_test24 extends Symmetry010Chronology_ESTest_scaffolding {

    /**
     * Verifies that the chronology can build a zoned date-time from a temporal
     * accessor (here an OffsetDateTime), returning a non-null result.
     */
    @Test(timeout = 4000)
    public void zonedDateTimeFromTemporalAccessorReturnsNonNull() throws Throwable {
        Symmetry010Chronology chronology = new Symmetry010Chronology();
        OffsetDateTime now = MockOffsetDateTime.now();

        ChronoZonedDateTime<Symmetry010Date> zonedDateTime =
                chronology.zonedDateTime((TemporalAccessor) now);

        assertNotNull(zonedDateTime);
    }
}
