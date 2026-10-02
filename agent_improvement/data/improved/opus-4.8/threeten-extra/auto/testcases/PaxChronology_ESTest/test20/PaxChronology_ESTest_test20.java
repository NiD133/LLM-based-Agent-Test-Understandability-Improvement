package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test20 extends PaxChronology_ESTest_scaffolding {

    /**
     * A positive epoch day falls after the Pax epoch (0001-01-01), so the
     * resulting date should belong to the Current Era (CE).
     */
    @Test(timeout = 4000)
    public void dateFromPositiveEpochDayIsInCurrentEra() throws Throwable {
        PaxChronology paxChronology = new PaxChronology();

        PaxDate dateAfterEpoch = paxChronology.dateEpochDay(65L);

        assertEquals(PaxEra.CE, dateAfterEpoch.getEra());
    }
}
