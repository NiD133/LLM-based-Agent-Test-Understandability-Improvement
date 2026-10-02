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
     * Verifies that {@link Frequency#toString()} renders a single recorded value
     * as a table row reporting a count of 1 and 100% for both the percentage and
     * the cumulative percentage columns.
     */
    @Test(timeout = 4000)
    public void toStringFormatsSingleValueWithFullPercentages() throws Throwable {
        // A stub comparator is enough: with only one distinct value it is never
        // actually consulted to order entries, so always returning "equal" (0) is safe.
        Comparator<Integer> stubComparator =
                (Comparator<Integer>) mock(Comparator.class, new ViolatedAssumptionAnswer());
        doReturn(0, 0, 0, 0, 0).when(stubComparator).compare(anyInt(), anyInt());

        Frequency<Integer> frequency = new Frequency<Integer>(stubComparator);

        // Record the value 46 exactly once.
        Integer recordedValue = new Integer(46);
        frequency.addValue(recordedValue);

        String table = frequency.toString();

        String expectedTable =
                "Value \t Freq. \t Pct. \t Cum Pct. \n"
                + "46\t1\t100%\t100%\n";
        assertEquals(expectedTable, table);
    }
}
