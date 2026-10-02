package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JulianChronology_ESTest_test07 extends JulianChronology_ESTest_scaffolding {

    /**
     * Verifies that a JulianDate created from epoch day 2 belongs to the AD era.
     * Also exercises the public JulianChronology constructor (deprecated but still accessible).
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        // Exercises the public constructor; the actual date lookup uses the singleton INSTANCE
        JulianChronology chronology = new JulianChronology();

        // Epoch day 2 corresponds to a date in the AD era of the Julian calendar
        JulianDate dateAtEpochDay2 = chronology.INSTANCE.dateEpochDay(2L);

        assertEquals(JulianEra.AD, dateAtEpochDay2.getEra());
    }
}
