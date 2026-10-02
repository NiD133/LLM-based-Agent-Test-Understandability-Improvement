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
public class Frequency_ESTest_test05 extends Frequency_ESTest_scaffolding {

    /**
     * Tests that getMode() returns only the value with the highest frequency count.
     *
     * Setup:
     *   - value 166 is added with count 0 (no actual occurrences)
     *   - value 0 is added with count 1 (one occurrence)
     *
     * Expected: the mode list contains exactly one element (value 0),
     * because 0 has the highest frequency (1 > 0).
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        Frequency<Integer> frequency = new Frequency<Integer>();

        Integer valueWithZeroCount = new Integer(166);
        Integer valueWithOneCount = new Integer(0);

        frequency.incrementValue(valueWithZeroCount, 0L);
        frequency.incrementValue(valueWithOneCount, 1L);

        List<Integer> mode = frequency.getMode();

        assertTrue("Mode should contain the value 0, which has the highest frequency", mode.contains(0));
        assertEquals("Mode list should contain exactly one element", 1, mode.size());
    }
}
