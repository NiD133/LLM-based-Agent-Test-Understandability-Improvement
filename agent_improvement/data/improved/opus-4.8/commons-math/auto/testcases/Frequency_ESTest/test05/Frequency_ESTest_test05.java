package org.apache.commons.math4.legacy.stat;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Frequency_ESTest_test05 extends Frequency_ESTest_scaffolding {

    /**
     * Verifies that getMode() returns the single most frequent value.
     *
     * The value 166 is added with a count of 0, while 0 is added with a count
     * of 1. Because 0 has the highest frequency, it is the sole mode.
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        Frequency<Integer> frequency = new Frequency<Integer>();

        Integer mostFrequentValue = 0;
        Integer neverCountedValue = 166;
        frequency.incrementValue(neverCountedValue, 0L);
        frequency.incrementValue(mostFrequentValue, 1L);

        List<Integer> mode = frequency.getMode();

        assertTrue(mode.contains(0));
        assertEquals(1, mode.size());
    }
}
