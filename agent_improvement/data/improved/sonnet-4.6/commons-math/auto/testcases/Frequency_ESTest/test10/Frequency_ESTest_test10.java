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
public class Frequency_ESTest_test10 extends Frequency_ESTest_scaffolding {

    /**
     * getCumFreq returns the count of all values less than or equal to the given value.
     * When only 1587 has been added, querying cumulative frequency for 1905 (which is
     * greater than 1587) should return 1, because one observation falls at or below 1905.
     */
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        Frequency<Integer> frequency = new Frequency<Integer>();

        Integer observedValue = new Integer(1587);
        frequency.addValue(observedValue);

        Integer queryValue = new Integer(1905);
        long cumulativeFreqUpToQueryValue = frequency.getCumFreq(queryValue);

        // 1905 > 1587, so all 1 observation falls at or below the query value
        assertEquals(1L, cumulativeFreqUpToQueryValue);
    }
}
