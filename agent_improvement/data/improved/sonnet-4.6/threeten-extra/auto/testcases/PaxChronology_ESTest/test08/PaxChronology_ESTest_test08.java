package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test08 extends PaxChronology_ESTest_scaffolding {

    /**
     * Verifies that plusYears() returns a new PaxDate instance rather than
     * mutating the original, confirming PaxDate immutability.
     */
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        PaxDate originalDate = PaxDate.ofEpochDay(146096L);
        PaxDate dateAfterAddingYears = originalDate.plusYears(146096L);
        assertNotSame(originalDate, dateAfterAddingYears);
    }
}
