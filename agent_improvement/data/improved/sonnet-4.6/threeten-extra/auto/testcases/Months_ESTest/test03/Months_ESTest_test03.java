package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.chrono.HijrahDate;
import java.time.temporal.Temporal;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.chrono.MockHijrahDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test03 extends Months_ESTest_scaffolding {

    /**
     * Verifies that subtractFrom returns a new Temporal object rather than
     * mutating the original HijrahDate, confirming Months immutability.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        Months oneMonth = Months.ONE;
        HijrahDate today = MockHijrahDate.now();

        Temporal dateMinusOneMonth = oneMonth.subtractFrom(today);

        assertNotSame(dateMinusOneMonth, today);
    }
}
