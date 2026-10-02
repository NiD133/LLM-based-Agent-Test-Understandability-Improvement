package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test20 extends Hours_ESTest_scaffolding {

    /**
     * Verifies that an {@link Hours} created from a zero-length {@link Duration}
     * reports zero when queried for its value in the HOURS unit.
     */
    @Test(timeout = 4000)
    public void get_withHoursUnit_returnsZeroForZeroDuration() throws Throwable {
        Hours zeroHours = Hours.from(Duration.ZERO);

        long hoursValue = zeroHours.get(ChronoUnit.HOURS);

        assertEquals(0L, hoursValue);
    }
}
