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
     * Verifies that adjusting a JulianDate with a ZoneOffset throws
     * UnsupportedTemporalTypeException, because ZoneOffset attempts to set
     * OffsetSeconds which AbstractDate does not support.
     */
    @Test(timeout = 4000)
    public void test_withZoneOffset_throwsUnsupportedTemporalTypeExceptionForOffsetSeconds() throws Throwable {
        JulianChronology chronology = JulianChronology.INSTANCE;
        ZoneOffset minZoneOffset = ZoneOffset.MIN;
        JulianDate todayInMinZone = chronology.dateNow((ZoneId) minZoneOffset);

        try {
            todayInMinZone.with((TemporalAdjuster) minZoneOffset);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            // ZoneOffset.adjustInto sets OffsetSeconds, which AbstractDate rejects
            verifyException("org.threeten.extra.chrono.AbstractDate", e);
        }
    }
}
