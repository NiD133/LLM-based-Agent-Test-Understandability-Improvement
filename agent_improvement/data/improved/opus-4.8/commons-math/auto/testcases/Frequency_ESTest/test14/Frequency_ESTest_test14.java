package org.apache.commons.math4.legacy.stat;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Frequency_ESTest_test14 extends Frequency_ESTest_scaffolding {

    /**
     * Querying the count of a value that was never added should return zero.
     */
    @Test(timeout = 4000)
    public void getCountForUnseenValueReturnsZero() throws Throwable {
        Frequency<Integer> emptyFrequency = new Frequency<Integer>();

        long countOfUnseenValue = emptyFrequency.getCount(0);

        assertEquals(0L, countOfUnseenValue);
    }
}
