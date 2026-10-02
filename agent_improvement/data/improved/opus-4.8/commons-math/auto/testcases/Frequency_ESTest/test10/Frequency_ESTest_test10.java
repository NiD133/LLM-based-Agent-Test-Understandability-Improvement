package org.apache.commons.math4.legacy.stat;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Frequency_ESTest_test10 extends Frequency_ESTest_scaffolding {

    /**
     * The cumulative frequency of a value counts every observation that is less
     * than or equal to it. Here a single observation (1587) is recorded, and the
     * cumulative frequency is queried for a larger value (1905). Since 1587 is
     * less than 1905, that one observation should be included, giving a count of 1.
     */
    @Test(timeout = 4000)
    public void cumulativeFrequencyIncludesAllValuesUpToQuery() throws Throwable {
        Frequency<Integer> frequency = new Frequency<Integer>();
        Integer recordedValue = Integer.valueOf(1587);
        frequency.addValue(recordedValue);

        Integer queryValue = Integer.valueOf(1905);
        long cumulativeFrequency = frequency.getCumFreq(queryValue);

        assertEquals(1L, cumulativeFrequency);
    }
}
