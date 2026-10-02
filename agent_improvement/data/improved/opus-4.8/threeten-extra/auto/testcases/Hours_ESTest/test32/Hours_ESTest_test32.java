package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test32 extends Hours_ESTest_scaffolding {

    /**
     * Verifies that comparing an Hours instance to itself returns zero,
     * and that the comparison does not alter the amount it holds.
     */
    @Test(timeout = 4000)
    public void compareToSelfReturnsZero() throws Throwable {
        Hours twoHours = Hours.of(2);

        int comparisonResult = twoHours.compareTo(twoHours);

        assertEquals(0, comparisonResult);
        assertEquals(2, twoHours.getAmount());
    }
}
