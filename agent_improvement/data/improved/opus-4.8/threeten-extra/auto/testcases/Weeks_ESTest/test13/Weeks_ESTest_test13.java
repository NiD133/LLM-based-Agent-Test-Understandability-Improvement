package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test13 extends Weeks_ESTest_scaffolding {

    /**
     * Adding a negative number of weeks to {@code Weeks.ONE} (1 week)
     * yields the algebraic sum: 1 + (-565) = -564.
     */
    @Test(timeout = 4000)
    public void plus_negativeAmount_returnsSum() throws Throwable {
        Weeks oneWeek = Weeks.ONE;

        Weeks result = oneWeek.plus(-565);

        assertEquals(-564, result.getAmount());
    }
}
