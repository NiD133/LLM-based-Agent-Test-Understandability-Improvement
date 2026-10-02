package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test24 extends Days_ESTest_scaffolding {

    /**
     * Zero weeks should convert to a zero-day amount,
     * so {@link Days#isZero()} must report true.
     */
    @Test(timeout = 4000)
    public void ofZeroWeeksIsZero() throws Throwable {
        Days zeroWeeks = Days.ofWeeks(0);

        assertTrue(zeroWeeks.isZero());
    }
}
