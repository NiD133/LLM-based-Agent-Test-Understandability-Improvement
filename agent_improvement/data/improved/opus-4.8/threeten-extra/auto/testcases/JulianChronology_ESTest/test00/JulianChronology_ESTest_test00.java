package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JulianChronology_ESTest_test00 extends JulianChronology_ESTest_scaffolding {

    /**
     * Adjusting a JulianDate with a ZoneOffset must fail.
     *
     * A ZoneOffset is a TemporalAdjuster that sets the OFFSET_SECONDS field, but a
     * JulianDate (a pure calendar date) does not support that field. The adjustment
     * is therefore expected to throw UnsupportedTemporalTypeException, raised from
     * AbstractDate, the shared base class of the threeten-extra calendar dates.
     */
    @Test(timeout = 4000)
    public void adjustingJulianDateWithZoneOffsetThrowsForUnsupportedField() throws Throwable {
        JulianChronology julianChronology = JulianChronology.INSTANCE;
        ZoneOffset zoneOffset = ZoneOffset.MIN;

        // Obtain today's Julian date in the given zone offset.
        JulianDate julianDate = julianChronology.dateNow((ZoneId) zoneOffset);

        try {
            // A ZoneOffset used as a TemporalAdjuster tries to set OFFSET_SECONDS,
            // which a calendar-only JulianDate cannot hold.
            julianDate.with((TemporalAdjuster) zoneOffset);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            // Unsupported field: OffsetSeconds
            verifyException("org.threeten.extra.chrono.AbstractDate", e);
        }
    }
}
