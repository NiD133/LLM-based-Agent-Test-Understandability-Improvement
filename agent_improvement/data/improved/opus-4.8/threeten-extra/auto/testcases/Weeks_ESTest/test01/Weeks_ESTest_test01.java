package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test01 extends Weeks_ESTest_scaffolding {

    /**
     * Subtracting one week from {@code Weeks.ONE} yields a zero amount,
     * which must not be considered equal to a negative amount of weeks.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Weeks minusOneWeek = Weeks.of(-1);
        Weeks zeroWeeks = Weeks.ONE.minus(1);

        // ONE minus one week collapses to zero.
        assertTrue(zeroWeeks.isZero());
        assertEquals(-1, minusOneWeek.getAmount());

        // -1 weeks and 0 weeks are different amounts, so they are not equal.
        assertFalse(minusOneWeek.equals(zeroWeeks));
        assertFalse(zeroWeeks.equals((Object) minusOneWeek));
    }
}
