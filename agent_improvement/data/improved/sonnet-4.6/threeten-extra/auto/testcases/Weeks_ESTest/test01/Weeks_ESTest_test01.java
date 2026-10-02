package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test01 extends Weeks_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Weeks negativeOneWeek = Weeks.of(-1);
        // Subtract 1 from Weeks.ONE (1 week) to produce 0 weeks
        Weeks zeroWeeks = Weeks.ONE.minus(1);
        boolean isEqual = negativeOneWeek.equals(zeroWeeks);

        assertTrue(zeroWeeks.isZero());
        assertFalse(isEqual);
        assertEquals(-1, negativeOneWeek.getAmount());
        assertFalse(zeroWeeks.equals((Object) negativeOneWeek));
    }
}
