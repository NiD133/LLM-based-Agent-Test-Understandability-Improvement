package org.apache.commons.math4.legacy.stat;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Frequency_ESTest_test13 extends Frequency_ESTest_scaffolding {

    /**
     * Verifies that querying the percentage of a value on an empty Frequency
     * returns NaN, since the percentage is computed as count / total and the
     * total count is zero (0 / 0 = NaN).
     */
    @Test(timeout = 4000)
    public void getPctOnEmptyFrequencyReturnsNaN() throws Throwable {
        Frequency<Integer> emptyFrequency = new Frequency<Integer>();
        Integer valueToLookUp = Integer.valueOf(0);

        double percentage = emptyFrequency.getPct(valueToLookUp);

        assertEquals(Double.NaN, percentage, 0.01);
    }
}
