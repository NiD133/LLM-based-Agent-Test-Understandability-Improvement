package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.chrono.IsoEra;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry010Chronology_ESTest_test15 extends Symmetry010Chronology_ESTest_scaffolding {

    /**
     * A Symmetry010 date with a positive epoch day falls after the epoch
     * (1970-01-01), so it belongs to the Current Era (CE).
     */
    @Test(timeout = 4000)
    public void epochDayAfterEpochIsInCommonEra() throws Throwable {
        Symmetry010Date dateAfterEpoch = Symmetry010Date.ofEpochDay(3);

        assertEquals(IsoEra.CE, dateAfterEpoch.getEra());
    }
}
