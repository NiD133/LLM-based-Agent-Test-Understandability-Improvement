package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test17 extends Weeks_ESTest_scaffolding {

    /**
     * The constant {@link Weeks#ONE} represents a single week, so it holds an
     * amount of 1 and is therefore not zero.
     */
    @Test(timeout = 4000)
    public void oneWeek_hasAmountOfOne_andIsNotZero() throws Throwable {
        Weeks oneWeek = Weeks.ONE;

        boolean isZero = oneWeek.isZero();

        assertEquals("Weeks.ONE should hold an amount of 1", 1, oneWeek.getAmount());
        assertFalse("Weeks.ONE should not be considered zero", isZero);
    }
}
