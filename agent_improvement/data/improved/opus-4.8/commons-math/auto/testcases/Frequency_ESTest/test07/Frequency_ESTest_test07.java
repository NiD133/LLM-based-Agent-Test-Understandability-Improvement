package org.apache.commons.math4.legacy.stat;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Frequency_ESTest_test07 extends Frequency_ESTest_scaffolding {

    /**
     * Verifies that getCumFreq returns 0 for a value that was never added with a
     * positive count. One value is incremented by 0 and another by -1, so the
     * frequency table holds no positive observations. Querying the cumulative
     * frequency of an unrelated value (0) must therefore yield 0.
     */
    @Test(timeout = 4000)
    public void cumulativeFrequencyIsZeroWhenNoPositiveCountsRecorded() throws Throwable {
        Frequency<Integer> frequency = new Frequency<Integer>();

        // Increment two values by non-positive amounts: neither adds a real observation.
        Integer valueIncrementedByZero = Integer.valueOf(-2146457125);
        frequency.incrementValue(valueIncrementedByZero, 0L);

        Integer valueDecremented = Integer.valueOf(221);
        frequency.incrementValue(valueDecremented, -1);

        // Cumulative frequency of an unrelated, unrecorded value should be 0.
        Integer queriedValue = Integer.valueOf(0);
        long cumulativeFrequency = frequency.getCumFreq(queriedValue);

        assertEquals(0L, cumulativeFrequency);
    }
}
