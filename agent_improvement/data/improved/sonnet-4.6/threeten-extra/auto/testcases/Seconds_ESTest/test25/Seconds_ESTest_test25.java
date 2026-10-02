package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test25 extends Seconds_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_ofHours_zero_returnsZeroSeconds() throws Throwable {
        // Converting 0 hours to seconds should always yield 0
        Seconds zeroHoursAsSeconds = Seconds.ofHours(0);
        assertEquals(0, zeroHoursAsSeconds.getAmount());
    }
}
