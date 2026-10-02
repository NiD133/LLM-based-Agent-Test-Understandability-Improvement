package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test09 extends Weeks_ESTest_scaffolding {

    // Dividing by 1 is a no-op: the method should return the same object with the same week count.
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        Weeks minusOneWeek = Weeks.of(-1);
        Weeks result = minusOneWeek.dividedBy(1);
        assertEquals(-1, result.getAmount());
        assertSame(result, minusOneWeek);
    }
}
