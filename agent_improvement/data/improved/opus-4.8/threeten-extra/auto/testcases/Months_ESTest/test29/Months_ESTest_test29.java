package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test29 extends Months_ESTest_scaffolding {

    /**
     * Comparing a Months value with itself should report equality,
     * so compareTo must return 0.
     */
    @Test(timeout = 4000)
    public void compareToSameInstanceReturnsZero() throws Throwable {
        Months oneMonth = Months.ONE;

        int comparison = oneMonth.compareTo(oneMonth);

        assertEquals(0, comparison);
    }
}
