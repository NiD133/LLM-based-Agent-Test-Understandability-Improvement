package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.ZoneId;
import java.time.chrono.IsoEra;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry010Chronology_ESTest_test20 extends Symmetry010Chronology_ESTest_scaffolding {

    /**
     * The current date obtained from {@code dateNow(ZoneId)} should fall in the
     * Common Era, since "now" is always a positive proleptic year.
     */
    @Test(timeout = 4000)
    public void dateNowInSystemZoneIsInCommonEra() throws Throwable {
        Symmetry010Chronology chronology = new Symmetry010Chronology();
        ZoneId systemZone = ZoneId.systemDefault();

        Symmetry010Date today = chronology.dateNow(systemZone);

        assertEquals(IsoEra.CE, today.getEra());
    }
}
