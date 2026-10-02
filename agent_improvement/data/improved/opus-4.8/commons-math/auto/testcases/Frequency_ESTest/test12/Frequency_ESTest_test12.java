package org.apache.commons.math4.legacy.stat;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Frequency_ESTest_test12 extends Frequency_ESTest_scaffolding {

    /**
     * Querying the cumulative frequency of a null value on an empty
     * Frequency should return 0, since no values have been added.
     */
    @Test(timeout = 4000)
    public void getCumFreqOfNullValueOnEmptyFrequencyReturnsZero() throws Throwable {
        Frequency<Integer> emptyFrequency = new Frequency<Integer>();

        long cumulativeFrequency = emptyFrequency.getCumFreq((Integer) null);

        assertEquals(0L, cumulativeFrequency);
    }
}
