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
     * Verifies that getCumFreq returns the cumulative count of all values less
     * than or equal to the queried value.
     *
     * Two values are recorded: a negative value incremented by 0, and a
     * positive value incremented by -1. Querying the cumulative frequency for 0
     * should sum only the counts of values <= 0. The negative value is the only
     * one that qualifies, and its count is 0, so the cumulative frequency is 0.
     */
    @Test(timeout = 4000)
    public void cumulativeFrequencyOfZeroIsZeroWhenSmallerValueHasZeroCount() throws Throwable {
        Frequency<Integer> frequency = new Frequency<Integer>();

        Integer negativeValue = -2146457125;
        frequency.incrementValue(negativeValue, 0L);

        Integer positiveValue = 221;
        frequency.incrementValue(positiveValue, -1L);

        Integer queriedValue = 0;
        long cumulativeFrequency = frequency.getCumFreq(queriedValue);

        assertEquals(0L, cumulativeFrequency);
    }
}
