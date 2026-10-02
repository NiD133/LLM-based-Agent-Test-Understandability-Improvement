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
     * Verifies that transforming [null, -2] into [0, -2, -2, 0] produces 4 modifications
     * (delete null, insert 0, keep -2, insert -2, insert 0).
     */
    @Test(timeout = 4000)
    public void test1() throws Throwable {
        Integer zero = new Integer(0);
        Integer negTwo = new Integer(-2);

        // sequence1 (source): [null, -2]
        LinkedList<Integer> sourceSequence = new LinkedList<Integer>();
        sourceSequence.add((Integer) null);
        sourceSequence.add(negTwo);

        // sequence2 (target): [0, -2, -2, 0]
        LinkedList<Integer> targetSequence = new LinkedList<Integer>();
        targetSequence.add(zero);
        targetSequence.add(negTwo);
        targetSequence.add(negTwo);
        targetSequence.add(zero);

        SequencesComparator<Integer> comparator = new SequencesComparator<Integer>(sourceSequence, targetSequence);
        EditScript<Integer> editScript = comparator.getScript();

        assertEquals(4, editScript.getModifications());
    }
}
