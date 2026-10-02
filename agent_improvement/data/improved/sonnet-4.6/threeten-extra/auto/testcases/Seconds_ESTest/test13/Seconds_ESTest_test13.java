package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test13 extends Seconds_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_plusZeroToZeroReturnsZero() throws Throwable {
        // Adding zero seconds to zero seconds should still yield zero seconds
        Seconds zero = Seconds.ZERO;
        Seconds result = zero.plus((TemporalAmount) zero);
        assertEquals(0, result.getAmount());
    }
}
