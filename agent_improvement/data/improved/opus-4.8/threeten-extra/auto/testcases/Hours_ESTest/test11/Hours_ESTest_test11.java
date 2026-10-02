package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test11 extends Hours_ESTest_scaffolding {

    /**
     * Dividing zero hours by any non-unit divisor still yields zero hours,
     * and zero is represented by the shared {@link Hours#ZERO} singleton.
     */
    @Test(timeout = 4000)
    public void dividingZeroHoursReturnsZeroSingleton() throws Throwable {
        Hours zeroHours = Hours.ZERO;

        Hours result = zeroHours.dividedBy(-3);

        assertSame(zeroHours, result);
    }
}
