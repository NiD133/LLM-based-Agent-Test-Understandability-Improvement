package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.ChronoUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test19 extends Minutes_ESTest_scaffolding {

    /**
     * Verifies that querying the MINUTES unit returns the total minutes held by
     * the amount. A Minutes built from -899 hours holds -899 * 60 = -53940 minutes.
     */
    @Test(timeout = 4000)
    public void get_withMinutesUnit_returnsTotalMinutes() throws Throwable {
        Minutes negativeNineHundredHours = Minutes.ofHours(-899);

        long minutesValue = negativeNineHundredHours.get(ChronoUnit.MINUTES);

        assertEquals(-53940L, minutesValue);
    }
}
