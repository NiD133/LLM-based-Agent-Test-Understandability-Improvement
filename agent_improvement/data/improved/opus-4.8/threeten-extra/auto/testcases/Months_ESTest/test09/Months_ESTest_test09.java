package org.threeten.extra;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test09 extends Months_ESTest_scaffolding {

    /**
     * Dividing zero months by one yields an amount that is still zero.
     */
    @Test(timeout = 4000)
    public void dividingZeroMonthsByOne_staysZero() throws Throwable {
        Months result = Months.ZERO.dividedBy(1);

        assertTrue(result.isZero());
    }
}
