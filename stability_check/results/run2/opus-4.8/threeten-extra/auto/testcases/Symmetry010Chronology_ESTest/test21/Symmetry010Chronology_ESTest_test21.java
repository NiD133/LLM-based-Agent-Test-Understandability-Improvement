package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.time.chrono.IsoEra;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry010Chronology_ESTest_test21 extends Symmetry010Chronology_ESTest_scaffolding {

    /**
     * A positive epoch day falls after the epoch (1970-01-01), so the resulting
     * date should be in the Common Era (CE).
     */
    @Test(timeout = 4000)
    public void dateEpochDay_withPositiveEpochDay_returnsDateInCommonEra() throws Throwable {
        Symmetry010Chronology chronology = Symmetry010Chronology.INSTANCE;

        Symmetry010Date date = chronology.dateEpochDay(702L);

        assertEquals(IsoEra.CE, date.getEra());
    }
}
