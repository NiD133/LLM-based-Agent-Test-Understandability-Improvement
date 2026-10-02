package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test02 extends Weeks_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_weeksOf922_equalsItselfAndReturnsCorrectAmount() throws Throwable {
        Weeks weeks = Weeks.of(922);

        // A Weeks instance must be reflexively equal to itself
        assertTrue(weeks.equals(weeks));

        // getAmount() must return the value used during construction
        assertEquals(922, weeks.getAmount());
    }
}
