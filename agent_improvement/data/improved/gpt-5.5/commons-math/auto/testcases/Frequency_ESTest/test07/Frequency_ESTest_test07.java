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
        Frequency<Integer> frequency = new Frequency<Integer>();

        Integer queriedValue = new Integer(0);
        Integer zeroIncrementValue = new Integer((-2146457125));
        Integer negativeIncrementValue = new Integer(221);

        frequency.incrementValue(zeroIncrementValue, 0L);
        frequency.incrementValue(negativeIncrementValue, (-1));

        long cumulativeFrequency = frequency.getCumFreq(queriedValue);
        assertEquals(0L, cumulativeFrequency);
    }
}
