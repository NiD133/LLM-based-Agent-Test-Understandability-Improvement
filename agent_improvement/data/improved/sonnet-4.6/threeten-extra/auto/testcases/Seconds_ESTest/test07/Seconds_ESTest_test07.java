package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import java.time.temporal.Temporal;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test07 extends Seconds_ESTest_scaffolding {

    /**
     * Verifies that addTo(null) on Seconds.ZERO returns null without throwing.
     *
     * The addTo implementation only calls temporal.plus(...) when the seconds value
     * is non-zero. Because ZERO holds 0 seconds, the null argument is returned
     * unchanged, demonstrating the short-circuit behaviour for zero amounts.
     */
    @Test(timeout = 4000)
    public void test_addTo_withNullTemporal_returnsNullWhenAmountIsZero() throws Throwable {
        Seconds zeroSeconds = Seconds.ZERO;

        Temporal result = zeroSeconds.addTo((Temporal) null);

        assertNull(result);
    }
}
