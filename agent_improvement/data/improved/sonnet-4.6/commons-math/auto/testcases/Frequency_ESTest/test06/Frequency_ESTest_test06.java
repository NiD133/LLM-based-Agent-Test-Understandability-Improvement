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
public class Frequency_ESTest_test06 extends Frequency_ESTest_scaffolding {

    // getCumPct returns NaN when the frequency table is empty (no observations recorded)
    @Test(timeout = 4000)
    public void test_getCumPct_returnsNaN_whenFrequencyTableIsEmpty() throws Throwable {
        Frequency<Integer> emptyFrequency = new Frequency<Integer>();
        Integer value = new Integer(0);

        double cumulativePercentage = emptyFrequency.getCumPct(value);

        assertEquals(Double.NaN, cumulativePercentage, 0.01);
    }
}
