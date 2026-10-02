package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test09 extends Weeks_ESTest_scaffolding {

    /**
     * Dividing a Weeks amount by 1 is a no-op: the value is unchanged and,
     * because the divisor is 1, the same instance is returned.
     */
    @Test(timeout = 4000)
    public void dividingByOneReturnsSameInstanceWithUnchangedAmount() throws Throwable {
        Weeks minusOneWeek = Weeks.of(-1);

        Weeks result = minusOneWeek.dividedBy(1);

        assertEquals(-1, result.getAmount());
        assertSame(minusOneWeek, result);
    }
}
