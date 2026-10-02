package org.apache.commons.math4.legacy.stat;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Frequency_ESTest_test07 extends Frequency_ESTest_scaffolding {

    /**
     * getCumFreq for a value that was never added should return 0, even when
     * other values have been recorded in the frequency table.
     */
    @Test(timeout = 4000)
    public void cumulativeFrequencyOfUnrecordedValueIsZero() throws Throwable {
        Frequency<Integer> frequency = new Frequency<Integer>();

        Integer recordedValue = new Integer(-2146457125);
        Integer anotherRecordedValue = new Integer(221);
        Integer unrecordedValue = new Integer(0);

        // Increment by zero, then by a negative amount; neither adds a positive count.
        frequency.incrementValue(recordedValue, 0L);
        frequency.incrementValue(anotherRecordedValue, -1);

        long cumulativeFrequency = frequency.getCumFreq(unrecordedValue);

        assertEquals(0L, cumulativeFrequency);
    }
}
