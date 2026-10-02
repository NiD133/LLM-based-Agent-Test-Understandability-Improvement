package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.time.chrono.IsoEra;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry010Chronology_ESTest_test17 extends Symmetry010Chronology_ESTest_scaffolding {

    /**
     * A date built for a positive proleptic year should belong to the Common Era (CE).
     */
    @Test(timeout = 4000)
    public void dateWithPositiveYearHasCommonEra() throws Throwable {
        Symmetry010Chronology chronology = Symmetry010Chronology.INSTANCE;

        Symmetry010Date date = chronology.date(9, 10, 10);

        assertEquals(IsoEra.CE, date.getEra());
    }
}
