package org.apache.commons.math4.legacy.stat;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Frequency_ESTest_test06 extends Frequency_ESTest_scaffolding {

    /**
     * Querying the cumulative percentage of a value on an empty Frequency
     * should return NaN, because there are no observations to divide by.
     */
    @Test(timeout = 4000)
    public void getCumPctOnEmptyFrequencyReturnsNaN() throws Throwable {
        Frequency<Integer> emptyFrequency = new Frequency<Integer>();
        Integer valueToLookUp = Integer.valueOf(0);

        double cumulativePercentage = emptyFrequency.getCumPct(valueToLookUp);

        assertEquals(Double.NaN, cumulativePercentage, 0.01);
    }
}
