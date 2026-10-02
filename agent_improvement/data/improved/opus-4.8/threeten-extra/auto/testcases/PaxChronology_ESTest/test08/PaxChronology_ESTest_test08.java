package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertNotSame;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test08 extends PaxChronology_ESTest_scaffolding {

    /**
     * Verifies that adding years to a PaxDate yields a new, distinct instance
     * rather than mutating the original date (PaxDate is immutable).
     */
    @Test(timeout = 4000)
    public void plusYears_returnsNewInstance() throws Throwable {
        PaxDate originalDate = PaxDate.ofEpochDay(146096L);

        PaxDate dateAfterAddingYears = originalDate.plusYears(146096L);

        assertNotSame(originalDate, dateAfterAddingYears);
    }
}
