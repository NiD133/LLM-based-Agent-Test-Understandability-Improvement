package org.apache.commons.math4.legacy.stat;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Frequency_ESTest_test08 extends Frequency_ESTest_scaffolding {

    /**
     * Verifies that the cumulative frequency of a value is zero when that value
     * was only added with non-positive increments.
     *
     * The first value receives an increment of 0, and a second, smaller value
     * receives a negative increment. Since the cumulative frequency counts the
     * total occurrences up to and including the queried value, and the queried
     * value itself was never positively incremented, the result is zero.
     */
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        Frequency<Integer> frequency = new Frequency<Integer>();

        Integer queriedValue = Integer.valueOf(0);
        Integer smallerValue = Integer.valueOf(-2146457125);

        // Add both values with non-positive increments, so neither
        // contributes a positive count to the distribution.
        frequency.incrementValue(queriedValue, 0L);
        frequency.incrementValue(smallerValue, -1);

        long cumulativeFrequency = frequency.getCumFreq(smallerValue);

        assertEquals(0L, cumulativeFrequency);
    }
}
