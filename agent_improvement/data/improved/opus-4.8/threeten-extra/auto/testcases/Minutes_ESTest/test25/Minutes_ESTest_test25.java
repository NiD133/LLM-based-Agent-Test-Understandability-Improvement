package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test25 extends Minutes_ESTest_scaffolding {

    /**
     * Verifies that calling hashCode() on the ZERO constant succeeds.
     * Since Minutes.hashCode() returns the underlying minute amount,
     * the hash code of the zero-minute constant is 0.
     */
    @Test(timeout = 4000)
    public void hashCodeOfZeroMinutesIsZero() throws Throwable {
        Minutes zeroMinutes = Minutes.ZERO;

        int hashCode = zeroMinutes.hashCode();

        assertEquals(0, hashCode);
    }
}
