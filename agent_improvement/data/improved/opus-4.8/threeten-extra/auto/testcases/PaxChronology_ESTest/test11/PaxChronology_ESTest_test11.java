package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test11 extends PaxChronology_ESTest_scaffolding {

    /**
     * Verifies that PaxChronology.eraOf(0) returns the BCE era.
     * The era value 0 corresponds to PaxEra.BCE, while 1 corresponds to PaxEra.CE.
     */
    @Test(timeout = 4000)
    public void eraOfZeroReturnsBce() throws Throwable {
        PaxDate paxDate = PaxDate.ofEpochDay(146096L);
        PaxChronology paxChronology = paxDate.getChronology();

        PaxEra era = paxChronology.eraOf(0);

        assertEquals(PaxEra.BCE, era);
    }
}
