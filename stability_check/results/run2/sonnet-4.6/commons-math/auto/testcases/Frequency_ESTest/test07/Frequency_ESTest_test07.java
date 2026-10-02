package org.apache.commons.math4.legacy.stat;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Frequency_ESTest_test07 extends Frequency_ESTest_scaffolding {

    /**
     * Verifies that getCumFreq returns 0 for a value (zero) that was never added to the frequency table,
     * even when other values have been incremented (including a negative increment).
     *
     * Setup:
     *   - incrementValue(-2146457125, 0): adds the value with a count of 0 (no real change)
     *   - incrementValue(221, -1): decrements the count of 221 by 1
     * Expectation:
     *   - getCumFreq(0) == 0, because 0 was never observed
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        Frequency<Integer> frequency = new Frequency<Integer>();

        Integer queryValue = new Integer(0);
        Integer largeNegativeValue = new Integer((-2146457125));
        Integer positiveValue = new Integer(221);

        // Add largeNegativeValue with a count of 0 (effectively a no-op for frequency)
        frequency.incrementValue(largeNegativeValue, 0L);

        // Decrement the count of positiveValue by 1
        frequency.incrementValue(positiveValue, (-1));

        // queryValue (0) was never added, so its cumulative frequency should be 0
        long cumulativeFreqOfQueryValue = frequency.getCumFreq(queryValue);
        assertEquals(0L, cumulativeFreqOfQueryValue);
    }
}
