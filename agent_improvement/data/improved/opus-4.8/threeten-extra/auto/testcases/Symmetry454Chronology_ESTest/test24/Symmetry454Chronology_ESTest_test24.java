package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.IsoEra;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry454Chronology_ESTest_test24 extends Symmetry454Chronology_ESTest_scaffolding {

    /**
     * The current date in the Symmetry454 calendar should always fall in the
     * Common Era (CE), regardless of which time zone is used to resolve "now".
     */
    @Test(timeout = 4000)
    public void dateNowForGivenZoneIsInCommonEra() throws Throwable {
        Symmetry454Chronology chronology = new Symmetry454Chronology();
        ZoneId zone = ZoneOffset.MAX;

        Symmetry454Date today = chronology.INSTANCE.dateNow(zone);

        assertEquals(IsoEra.CE, today.getEra());
    }
}
