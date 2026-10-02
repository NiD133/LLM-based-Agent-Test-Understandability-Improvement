package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test18 extends Seconds_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_ofMinutes_negativeInput_producesNegativeSeconds() throws Throwable {
        // -1 minute should convert to -60 seconds
        Seconds seconds = Seconds.ofMinutes(-1);

        assertEquals(-60, seconds.getAmount());
        assertTrue(seconds.isNegative());
    }
}
