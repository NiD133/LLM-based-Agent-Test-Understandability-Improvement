package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test11 extends Weeks_ESTest_scaffolding {

    // Multiplying by 1 is an identity operation — the week count must remain unchanged.
    @Test(timeout = 4000)
    public void test_multipliedByOne_returnsUnchangedAmount() throws Throwable {
        Weeks oneWeek = Weeks.of(1);
        Weeks result = oneWeek.multipliedBy(1);
        assertEquals(1, result.getAmount());
    }
}
