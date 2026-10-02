package org.apache.commons.collections4.sequence;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SequencesComparator_ESTest_test3 extends SequencesComparator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        final LinkedList<Integer> sourceSequence = new LinkedList<Integer>();
        final Integer repeatedValue = new Integer(4);
        sourceSequence.add(repeatedValue);
        sourceSequence.add(repeatedValue);

        final LinkedList<Integer> targetSequence = new LinkedList<Integer>();
        sourceSequence.add(repeatedValue);
        targetSequence.add((Integer) null);

        final SequencesComparator<Integer> comparator =
                new SequencesComparator<Integer>(targetSequence, sourceSequence);
        final EditScript<Integer> editScript = comparator.getScript();

        assertEquals(4, editScript.getModifications());
    }
}
