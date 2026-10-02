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

    // Comparing [null] against [4, 4, 4] requires deleting null and inserting three 4s = 4 modifications.
    @Test(timeout = 4000)
    public void test3() throws Throwable {
        Integer four = new Integer(4);

        LinkedList<Integer> sequenceWithNull = new LinkedList<Integer>();
        sequenceWithNull.add((Integer) null);

        LinkedList<Integer> sequenceWithFours = new LinkedList<Integer>();
        sequenceWithFours.add(four);
        sequenceWithFours.add(four);
        sequenceWithFours.add(four);

        SequencesComparator<Integer> comparator = new SequencesComparator<Integer>(sequenceWithNull, sequenceWithFours);
        EditScript<Integer> editScript = comparator.getScript();

        assertEquals(4, editScript.getModifications());
    }
}
