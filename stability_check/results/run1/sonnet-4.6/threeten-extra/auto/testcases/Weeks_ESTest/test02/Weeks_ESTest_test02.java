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
    public void test_weeksInstanceIsEqualToItself() throws Throwable {
        // A Weeks instance must satisfy the reflexive equality contract
        Weeks weeks922 = Weeks.of(922);
        assertTrue(weeks922.equals(weeks922));
        assertEquals(922, weeks922.getAmount());
    }
}
