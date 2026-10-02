package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UtcInstant_ESTest_test09 extends UtcInstant_ESTest_scaffolding {

    /**
     * Two instants on the same Modified Julian Day are ordered by their
     * nano-of-day. The earlier instant (smaller nano-of-day) is before the
     * later one.
     */
    @Test(timeout = 4000)
    public void isBefore_sameDayEarlierNanoOfDay_returnsTrue() throws Throwable {
        long modifiedJulianDay = 13L;
        UtcInstant earlierInstant = UtcInstant.ofModifiedJulianDay(modifiedJulianDay, 13L);
        UtcInstant laterInstant = UtcInstant.ofModifiedJulianDay(modifiedJulianDay, 86398161000000L);

        boolean earlierIsBeforeLater = earlierInstant.isBefore(laterInstant);

        assertTrue("earlier nano-of-day should be before later nano-of-day", earlierIsBeforeLater);
        assertEquals(modifiedJulianDay, laterInstant.getModifiedJulianDay());
    }
}
