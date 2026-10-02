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

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        // Create a frequency table for integers
        Frequency<Integer> frequency = new Frequency<Integer>();

        // Define the value we will query cumulative frequency for
        Integer queryValue = new Integer(0);

        // Add a large negative value with zero count (no-op increment)
        Integer largeNegative = new Integer((-2146457125));
        frequency.incrementValue(largeNegative, 0L);

        // Add value 221 with a negative count (-1), which removes one occurrence
        Integer value221 = new Integer(221);
        frequency.incrementValue(value221, (-1));

        // Cumulative frequency of queryValue (0) should be 0
        // because it was never added to the frequency table
        long cumFreqOfZero = frequency.getCumFreq(queryValue);
        assertEquals(0L, cumFreqOfZero);
    }
}
