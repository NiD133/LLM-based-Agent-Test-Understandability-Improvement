package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.time.chrono.IsoEra;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry454Chronology_ESTest_test21 extends Symmetry454Chronology_ESTest_scaffolding {

    /**
     * The current date produced by the Symmetry454 chronology should fall in the
     * Common Era (CE), since the present day is well after the ISO epoch.
     */
    @Test(timeout = 4000)
    public void dateNow_returnsDateInCommonEra() throws Throwable {
        Symmetry454Chronology chronology = Symmetry454Chronology.INSTANCE;

        Symmetry454Date today = chronology.dateNow();

        assertEquals(IsoEra.CE, today.getEra());
    }
}
