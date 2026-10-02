package org.apache.commons.math4.legacy.stat;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Frequency_ESTest_test09 extends Frequency_ESTest_scaffolding {

    /**
     * The cumulative frequency of a value counts how many recorded observations
     * are less than or equal to that value. When the only recorded observation
     * (1587) is greater than the queried value, the cumulative frequency is 0.
     */
    @Test(timeout = 4000)
    public void cumulativeFrequencyOfSmallerValueIsZero() throws Throwable {
        Frequency<Integer> frequency = new Frequency<Integer>();
        Integer recordedValue = Integer.valueOf(1587);
        frequency.addValue(recordedValue);

        // Integer.getInteger looks up a system property by name; the property is
        // absent, so it falls back to the supplied default of 123.
        Integer queriedValue = Integer.getInteger("Value \t Freq. \t Pct. \t Cum Pct. \n", 123);

        long cumulativeFrequency = frequency.getCumFreq(queriedValue);

        assertEquals(0L, cumulativeFrequency);
    }
}
