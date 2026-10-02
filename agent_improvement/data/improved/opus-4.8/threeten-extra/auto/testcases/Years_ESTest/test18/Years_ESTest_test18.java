package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test18 extends Years_ESTest_scaffolding {

    /**
     * Dividing one year by -1 yields negative one year, which reports itself
     * as negative. The original positive value remains unchanged (immutability).
     */
    @Test(timeout = 4000)
    public void dividingOneYearByMinusOneGivesNegativeOneYear() throws Throwable {
        Years oneYear = Years.ONE;
        Years negativeOneYear = oneYear.dividedBy(-1);

        assertEquals(-1, negativeOneYear.getAmount());
        assertTrue("negative one year should be negative", negativeOneYear.isNegative());
        assertFalse("the original one year should remain positive", oneYear.isNegative());
    }
}
