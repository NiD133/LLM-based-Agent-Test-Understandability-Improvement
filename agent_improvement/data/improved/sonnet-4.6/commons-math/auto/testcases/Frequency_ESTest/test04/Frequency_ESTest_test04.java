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
public class Frequency_ESTest_test04 extends Frequency_ESTest_scaffolding {

    // Verifies that merging a collection of Frequency objects into another
    // does not alter the size of the source collection.
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        Frequency<Integer> frequency = new Frequency<Integer>();
        Integer zero = new Integer(0);
        frequency.incrementValue(zero, 0L);

        LinkedList<Frequency<Integer>> sourceFrequencies = new LinkedList<Frequency<Integer>>();
        sourceFrequencies.add(frequency);

        frequency.merge((Collection<Frequency<Integer>>) sourceFrequencies);

        assertEquals("Source collection size should remain unchanged after merge", 1, sourceFrequencies.size());
    }
}
