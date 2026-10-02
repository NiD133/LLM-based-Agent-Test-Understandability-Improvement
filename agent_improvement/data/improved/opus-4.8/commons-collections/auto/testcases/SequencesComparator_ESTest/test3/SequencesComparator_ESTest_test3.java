package org.apache.commons.collections4.sequence;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedList;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SequencesComparator_ESTest_test3 extends SequencesComparator_ESTest_scaffolding {

    /**
     * Comparing a one-element sequence to a three-element sequence with no
     * common elements requires transforming [null] into [4, 4, 4]: one delete
     * for the null plus three inserts for the 4s, i.e. 4 modifications.
     */
    @Test(timeout = 4000)
    public void test3() throws Throwable {
        Integer four = Integer.valueOf(4);

        LinkedList<Integer> targetSequence = new LinkedList<Integer>();
        targetSequence.add(four);
        targetSequence.add(four);
        targetSequence.add(four);

        LinkedList<Integer> sourceSequence = new LinkedList<Integer>();
        sourceSequence.add((Integer) null);

        SequencesComparator<Integer> comparator =
                new SequencesComparator<Integer>(sourceSequence, targetSequence);
        EditScript<Integer> editScript = comparator.getScript();

        assertEquals(4, editScript.getModifications());
    }
}
