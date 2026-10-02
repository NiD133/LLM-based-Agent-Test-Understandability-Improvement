package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test11 extends PaxChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void eraOf_withZeroValue_returnsBCE() throws Throwable {
        PaxDate date = PaxDate.ofEpochDay(146096L);
        PaxChronology chronology = date.getChronology();
        PaxEra era = chronology.eraOf(0);
        assertEquals(PaxEra.BCE, era);
    }
}
