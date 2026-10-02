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
public class BritishCutoverChronology_ESTest_test20 extends BritishCutoverChronology_ESTest_scaffolding {

    /**
     * Verifies that the chronology can build a zoned date-time from an arbitrary
     * temporal source (here, the current {@link OffsetDateTime}).
     */
    @Test(timeout = 4000)
    public void zonedDateTimeFromTemporalReturnsNonNull() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();
        OffsetDateTime now = MockOffsetDateTime.now();

        ChronoZonedDateTime<BritishCutoverDate> zonedDateTime =
                chronology.zonedDateTime((TemporalAccessor) now);

        assertNotNull(zonedDateTime);
    }
}
