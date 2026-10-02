package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JulianChronology_ESTest_test07 extends JulianChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        JulianChronology chronology = new JulianChronology();

        JulianDate dateAtEpochDayTwo = chronology.INSTANCE.dateEpochDay(2L);

        assertEquals(JulianEra.AD, dateAtEpochDayTwo.getEra());
    }
}
