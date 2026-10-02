package org.threeten.extra.chrono;

import static org.junit.Assert.assertEquals;

import java.time.chrono.IsoEra;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry454Chronology_ESTest_test22 extends Symmetry454Chronology_ESTest_scaffolding {

    /**
     * A date created from an epoch day in the common era should report the ISO
     * era CE, since the Symmetry454 calendar shares the ISO era system.
     */
    @Test(timeout = 4000)
    public void dateFromEpochDayHasCommonEra() throws Throwable {
        Symmetry454Chronology chronology = Symmetry454Chronology.INSTANCE;

        // Epoch day 1 corresponds to 1970-01-02, which is in the common era.
        Symmetry454Date dateFromEpochDay = chronology.dateEpochDay(1);

        assertEquals(IsoEra.CE, dateFromEpochDay.getEra());
    }
}
