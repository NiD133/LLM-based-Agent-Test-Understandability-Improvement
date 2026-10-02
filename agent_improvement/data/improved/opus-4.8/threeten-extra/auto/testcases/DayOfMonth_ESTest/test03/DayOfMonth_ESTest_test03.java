package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfMonth_ESTest_test03 extends DayOfMonth_ESTest_scaffolding {

    /**
     * Verifies that a DayOfMonth obtained from the (mocked) system clock holds the
     * expected day value and that equals() is reflexive (an instance equals itself).
     */
    @Test(timeout = 4000)
    public void now_returnsExpectedDay_andEqualsIsReflexive() throws Throwable {
        DayOfMonth today = DayOfMonth.now();

        boolean equalsItself = today.equals(today);

        assertEquals(14, today.getValue());
        assertTrue(equalsItself);
    }
}
