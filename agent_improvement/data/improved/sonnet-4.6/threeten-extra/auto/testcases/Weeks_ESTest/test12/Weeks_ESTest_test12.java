package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test12 extends Weeks_ESTest_scaffolding {

    // Subtracting zero weeks from zero weeks should return the ZERO singleton unchanged.
    @Test(timeout = 4000)
    public void test_subtractingZeroWeeksFromZeroWeeksReturnsSameSingleton() throws Throwable {
        Weeks zero = Weeks.ZERO;
        Weeks result = zero.minus((TemporalAmount) zero);
        assertSame(zero, result);
    }
}
