package org.apache.commons.collections4.sequence;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedList;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SequencesComparator_ESTest_test4 extends SequencesComparator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test4() throws Throwable {
        // sequence1 = [null]; sequence2 = [null, -363, -363]
        // Transforming sequence1 into sequence2 requires 2 insertions, so getModifications() == 2
        LinkedList<Integer> sequence1 = new LinkedList<Integer>();
        sequence1.add((Integer) null);

        LinkedList<Integer> sequence2 = new LinkedList<Integer>(sequence1);
        Integer repeatedValue = new Integer((-363));
        sequence2.add(repeatedValue);
        sequence2.add(repeatedValue);

        SequencesComparator<Integer> comparator = new SequencesComparator<Integer>(sequence1, sequence2);
        EditScript<Integer> editScript = comparator.getScript();
        assertEquals(2, editScript.getModifications());
    }
}
