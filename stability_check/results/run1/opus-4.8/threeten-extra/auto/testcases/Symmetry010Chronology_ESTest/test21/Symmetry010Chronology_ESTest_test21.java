package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.chrono.IsoEra;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry010Chronology_ESTest_test21 extends Symmetry010Chronology_ESTest_scaffolding {

    /**
     * A positive epoch day maps to a date after the epoch (1970-01-01),
     * so the resulting date must fall in the Current Era (CE).
     */
    @Test(timeout = 4000)
    public void dateFromPositiveEpochDayIsInCurrentEra() throws Throwable {
        Symmetry010Chronology chronology = new Symmetry010Chronology();

        Symmetry010Date dateAfterEpoch = chronology.INSTANCE.dateEpochDay(702L);

        assertEquals(IsoEra.CE, dateAfterEpoch.getEra());
    }
}
