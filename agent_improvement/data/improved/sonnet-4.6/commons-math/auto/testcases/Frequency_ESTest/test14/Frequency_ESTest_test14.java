package org.apache.commons.math4.legacy.stat;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Frequency_ESTest_test14 extends Frequency_ESTest_scaffolding {

    /**
     * Verifies that getCount returns 0 for a value that has never been added to the frequency table.
     */
    @Test(timeout = 4000)
    public void test_getCount_returnsZero_whenValueNotAdded() throws Throwable {
        Frequency<Integer> frequency = new Frequency<Integer>();
        Integer value = new Integer(0);

        long count = frequency.getCount(value);

        assertEquals(0L, count);
    }
}
