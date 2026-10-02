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
public class Frequency_ESTest_test08 extends Frequency_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        Frequency<Integer> frequency = new Frequency<Integer>();
        Integer zero = new Integer(0);
        Integer negativeValue = new Integer((-2146457125));

        // Adding zero occurrences of negativeValue has no effect on its count
        frequency.incrementValue(negativeValue, 0L);
        // Decrementing the count of zero by 1
        frequency.incrementValue(zero, (-1));

        // Since negativeValue was incremented by 0, its cumulative frequency is 0
        long cumFreqAtNegativeValue = frequency.getCumFreq(negativeValue);
        assertEquals(0L, cumFreqAtNegativeValue);
    }
}
