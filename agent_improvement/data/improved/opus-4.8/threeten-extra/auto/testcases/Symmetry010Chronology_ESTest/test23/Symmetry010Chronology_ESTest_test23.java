package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.chrono.IsoEra;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry010Chronology_ESTest_test23 extends Symmetry010Chronology_ESTest_scaffolding {

    /**
     * The current date produced by {@link Symmetry010Chronology#dateNow()} should fall
     * in the Common Era, since the system clock always reports a present-day (CE) date.
     */
    @Test(timeout = 4000)
    public void dateNowReturnsDateInCommonEra() throws Throwable {
        Symmetry010Chronology chronology = new Symmetry010Chronology();

        Symmetry010Date today = chronology.dateNow();

        assertEquals(IsoEra.CE, today.getEra());
    }
}
