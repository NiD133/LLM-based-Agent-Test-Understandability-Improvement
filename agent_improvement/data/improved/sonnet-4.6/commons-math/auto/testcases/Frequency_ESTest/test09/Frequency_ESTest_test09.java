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
public class Frequency_ESTest_test09 extends Frequency_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        Frequency<Integer> frequency = new Frequency<Integer>();
        Integer addedValue = new Integer(1587);
        frequency.addValue(addedValue);

        // Integer.getInteger looks up the system property named by this string;
        // since no such property exists at runtime, it returns the default value 123.
        Integer queryValue = Integer.getInteger("Value \t Freq. \t Pct. \t Cum Pct. \n", 123);

        // queryValue (123) was never added to the frequency table, so its cumulative frequency is 0.
        long cumulativeFrequency = frequency.getCumFreq(queryValue);
        assertEquals(0L, cumulativeFrequency);
    }
}
