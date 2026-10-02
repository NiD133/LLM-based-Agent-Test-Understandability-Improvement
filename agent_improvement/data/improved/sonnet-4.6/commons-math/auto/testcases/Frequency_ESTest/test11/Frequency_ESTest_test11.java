package org.apache.commons.math4.legacy.stat;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.Comparator;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Frequency_ESTest_test11 extends Frequency_ESTest_scaffolding {

    /**
     * Verifies that toString() produces a correctly formatted frequency table
     * when a single integer value is added using a custom comparator.
     *
     * A mocked comparator that always returns 0 (equal) is used to control ordering.
     * After adding the value 46 once, the table should show 100% frequency and
     * 100% cumulative percentage for that single entry.
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        // Create a mock comparator that always considers integers as equal (returns 0)
        Comparator<Integer> alwaysEqualComparator = (Comparator<Integer>) mock(Comparator.class, new ViolatedAssumptionAnswer());
        doReturn(0, 0, 0, 0, 0).when(alwaysEqualComparator).compare(anyInt(), anyInt());

        // Build a Frequency table using the custom comparator
        Frequency<Integer> frequency = new Frequency<Integer>(alwaysEqualComparator);
        Integer value = new Integer(46);
        frequency.addValue(value);

        // The table should contain one row for value 46 with count=1, 100% frequency, 100% cumulative
        String expectedTable = "Value \t Freq. \t Pct. \t Cum Pct. \n46\t1\t100%\t100%\n";
        String actualTable = frequency.toString();
        assertEquals(expectedTable, actualTable);
    }
}
