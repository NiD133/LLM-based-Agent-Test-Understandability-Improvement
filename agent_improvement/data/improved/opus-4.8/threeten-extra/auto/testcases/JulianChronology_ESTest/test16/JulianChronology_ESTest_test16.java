package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.temporal.TemporalAccessor;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JulianChronology_ESTest_test16 extends JulianChronology_ESTest_scaffolding {

    /**
     * Verifies that the Julian chronology can build a zoned date-time from an
     * arbitrary {@link TemporalAccessor}: converting an ISO {@link ZonedDateTime}
     * should yield a non-null Julian {@link ChronoZonedDateTime}.
     */
    @Test(timeout = 4000)
    public void zonedDateTimeFromTemporalAccessorIsNotNull() throws Throwable {
        JulianChronology julianChronology = new JulianChronology();
        ZonedDateTime isoZonedDateTime = MockZonedDateTime.now();

        ChronoZonedDateTime<JulianDate> julianZonedDateTime =
                julianChronology.INSTANCE.zonedDateTime((TemporalAccessor) isoZonedDateTime);

        assertNotNull(julianZonedDateTime);
    }
}
