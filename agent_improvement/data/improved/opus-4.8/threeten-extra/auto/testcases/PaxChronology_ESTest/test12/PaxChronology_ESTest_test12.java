package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test12 extends PaxChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void toString_returnsChronologyId() throws Throwable {
        PaxChronology paxChronology = PaxChronology.INSTANCE;

        String chronologyId = paxChronology.toString();

        assertEquals("Pax", chronologyId);
    }
}
