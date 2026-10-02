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
     * Verifies that getCumFreq returns 0 for a value that has never been added to the frequency table,
     * even when other values have been incremented (including with zero and negative counts).
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        Frequency<Integer> frequency = new Frequency<Integer>();

        // Add a value with zero count — effectively a no-op
        Integer negativeValue = new Integer((-2146457125));
        frequency.incrementValue(negativeValue, 0L);

        // Add another value with a negative count
        Integer positiveValue = new Integer(221);
        frequency.incrementValue(positiveValue, (-1));

        // Query cumulative frequency for a value that was never added
        Integer queryValue = new Integer(0);
        long cumulativeFreq = frequency.getCumFreq(queryValue);

        // Since queryValue was never added, its cumulative frequency should be 0
        assertEquals(0L, cumulativeFreq);
    }
}
