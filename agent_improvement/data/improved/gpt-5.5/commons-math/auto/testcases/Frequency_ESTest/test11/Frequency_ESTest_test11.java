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
public class Frequency_ESTest_test11 extends Frequency_ESTest_scaffolding {

    private static final Integer OBSERVED_VALUE = new Integer(46);
    private static final String EXPECTED_SINGLE_VALUE_TABLE =
            "Value \t Freq. \t Pct. \t Cum Pct. \n46\t1\t100%\t100%\n";

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        Comparator<Integer> equalOrderingComparator =
                (Comparator<Integer>) mock(Comparator.class, new ViolatedAssumptionAnswer());
        doReturn(0, 0, 0, 0, 0).when(equalOrderingComparator).compare(anyInt(), anyInt());

        Frequency<Integer> frequency = new Frequency<Integer>(equalOrderingComparator);
        frequency.addValue(OBSERVED_VALUE);

        String frequencyTable = frequency.toString();
        assertEquals(EXPECTED_SINGLE_VALUE_TABLE, frequencyTable);
    }
}
