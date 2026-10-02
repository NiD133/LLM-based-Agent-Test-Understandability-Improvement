package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test12 extends Years_ESTest_scaffolding {

    /**
     * Multiplying a one-year amount by a scalar of 1 should leave the
     * amount unchanged at one year.
     */
    @Test(timeout = 4000)
    public void multipliedByOne_keepsAmountUnchanged() throws Throwable {
        Years oneYear = Years.ONE;

        Years result = oneYear.multipliedBy(1);

        assertEquals(1, result.getAmount());
    }
}
