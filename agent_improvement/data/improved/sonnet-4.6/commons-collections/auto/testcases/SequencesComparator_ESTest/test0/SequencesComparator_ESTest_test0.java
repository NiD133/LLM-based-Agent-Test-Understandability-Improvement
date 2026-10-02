package org.apache.commons.collections4.sequence;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedList;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SequencesComparator_ESTest_test0 extends SequencesComparator_ESTest_scaffolding {

    /**
     * Compares [null, 11, null] against [null].
     * The LCS is [null] (first element), so the edit script requires
     * 2 modifications: delete 11 and delete the trailing null.
     */
    @Test(timeout = 4000)
    public void test0() throws Throwable {
        // Target sequence: [null]
        LinkedList<Integer> targetSequence = new LinkedList<Integer>();
        targetSequence.add((Integer) null);

        // Source sequence built as: [null] -> [null, 11] -> [null, 11, null]
        LinkedList<Integer> sourceSequence = new LinkedList<Integer>(targetSequence);
        sourceSequence.add(Integer.valueOf(11));
        sourceSequence.addAll(targetSequence);

        // Comparing [null, 11, null] to [null] should require exactly 2 modifications
        SequencesComparator<Integer> comparator = new SequencesComparator<Integer>(sourceSequence, targetSequence);
        EditScript<Integer> editScript = comparator.getScript();

        assertEquals(2, editScript.getModifications());
    }
}
