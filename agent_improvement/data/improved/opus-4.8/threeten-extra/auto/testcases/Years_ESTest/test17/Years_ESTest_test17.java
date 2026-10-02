package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test17 extends Years_ESTest_scaffolding {

    /**
     * The constant {@link Years#ONE} represents an amount of one year, so it
     * should report an amount of 1 and should not be considered zero.
     */
    @Test(timeout = 4000)
    public void oneYear_isNotZero_andHasAmountOne() throws Throwable {
        Years oneYear = Years.ONE;

        assertFalse("ONE should not be reported as zero", oneYear.isZero());
        assertEquals("ONE should hold an amount of 1", 1, oneYear.getAmount());
    }
}
