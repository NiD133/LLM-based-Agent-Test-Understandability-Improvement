package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test01 extends Years_ESTest_scaffolding {

    /**
     * Verifies that subtracting one year from {@link Years#ZERO} yields a value
     * equal to {@code Years.of(-1)}, and that this negative amount is distinct
     * from the positive {@link Years#ONE} constant.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Years one = Years.ONE;
        Years minusOneViaPlus = Years.ZERO.plus(-1);
        Years minusOne = Years.of(-1);

        // ZERO.plus(-1) and Years.of(-1) represent the same amount.
        assertEquals(-1, minusOneViaPlus.getAmount());
        assertTrue(minusOne.equals(minusOneViaPlus));

        // The negative amount is not equal to the positive ONE constant (either direction).
        assertFalse(minusOne.equals(one));
        assertFalse(one.equals(minusOne));
    }
}
