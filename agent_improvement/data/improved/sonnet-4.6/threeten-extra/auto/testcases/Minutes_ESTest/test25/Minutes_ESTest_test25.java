package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test25 extends Minutes_ESTest_scaffolding {

    /**
     * Minutes.ZERO has an internal value of 0, and hashCode() returns that value directly,
     * so the hash code of ZERO should be 0.
     */
    @Test(timeout = 4000)
    public void test25_hashCodeOfZeroMinutesIsZero() throws Throwable {
        Minutes zeroMinutes = Minutes.ZERO;
        int hashCode = zeroMinutes.hashCode();
        assertEquals(0, hashCode);
    }
}
