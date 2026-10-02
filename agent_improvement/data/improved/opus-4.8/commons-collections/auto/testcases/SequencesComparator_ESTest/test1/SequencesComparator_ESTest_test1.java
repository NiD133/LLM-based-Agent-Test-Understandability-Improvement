package org.apache.commons.collections4.sequence;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedList;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SequencesComparator_ESTest_test1 extends SequencesComparator_ESTest_scaffolding {

    /**
     * Transforming the first sequence [null, -2] into the second sequence
     * [0, -2, -2, 0] requires 4 modifications (insert/delete commands); the
     * single shared element keeps the count below the total element count.
     */
    @Test(timeout = 4000)
    public void getScriptReportsModificationCountForDivergingSequences() throws Throwable {
        // First sequence: [null, -2]
        LinkedList<Integer> firstSequence = new LinkedList<Integer>();
        firstSequence.add((Integer) null);
        firstSequence.add(Integer.valueOf(-2));

        // Second sequence: [0, -2, -2, 0]
        LinkedList<Integer> secondSequence = new LinkedList<Integer>();
        secondSequence.add(Integer.valueOf(0));
        secondSequence.add(Integer.valueOf(-2));
        secondSequence.add(Integer.valueOf(-2));
        secondSequence.add(Integer.valueOf(0));

        SequencesComparator<Integer> comparator =
                new SequencesComparator<Integer>(firstSequence, secondSequence);

        EditScript<Integer> editScript = comparator.getScript();

        assertEquals(4, editScript.getModifications());
    }
}
