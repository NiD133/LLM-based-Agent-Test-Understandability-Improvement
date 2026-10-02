package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UtcInstant_ESTest_test18 extends UtcInstant_ESTest_scaffolding {

    /**
     * Adding a whole number of minutes to a UtcInstant shifts it only within the
     * same day here, so the Modified Julian Day must stay unchanged.
     */
    @Test(timeout = 4000)
    public void plusWholeMinutesKeepsModifiedJulianDay() throws Throwable {
        // Build a UTC instant from a TAI instant just before the TAI epoch.
        TaiInstant taiInstant = TaiInstant.ofTaiSeconds(-745L, -1000L);
        UtcInstant utcInstant = UtcInstant.of(taiInstant);

        long expectedModifiedJulianDay = 36203L;
        assertEquals(expectedModifiedJulianDay, utcInstant.getModifiedJulianDay());

        // Adding -1000 minutes stays within the same day, so the MJD is preserved.
        UtcInstant shiftedInstant = utcInstant.plus(Duration.ofMinutes(-1000L));
        assertEquals(expectedModifiedJulianDay, shiftedInstant.getModifiedJulianDay());
    }
}
