package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.chrono.IsoEra;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry454Chronology_ESTest_test21 extends Symmetry454Chronology_ESTest_scaffolding {

    /**
     * Verifies that a Symmetry454 date obtained from the current system clock
     * belongs to the Common Era (CE), since dates after year 0 are in CE.
     */
    @Test(timeout = 4000)
    public void test_dateNow_eraIsCommonEra() throws Throwable {
        Symmetry454Chronology chronology = Symmetry454Chronology.INSTANCE;
        Symmetry454Date today = chronology.dateNow();
        assertEquals("The current Symmetry454 date should be in the Common Era (CE)",
                IsoEra.CE, today.getEra());
    }
}
