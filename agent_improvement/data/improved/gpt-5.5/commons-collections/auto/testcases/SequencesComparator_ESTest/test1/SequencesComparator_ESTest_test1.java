package org.apache.commons.collections4.sequence;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Collection;
import java.util.LinkedList;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SequencesComparator_ESTest_test1 extends SequencesComparator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        LinkedList<Integer> targetSequence = new LinkedList<Integer>();
        Integer zero = new Integer(0);
        targetSequence.add(zero);
        Integer minusTwo = new Integer((-2));
        targetSequence.add(minusTwo);
        targetSequence.add(minusTwo);
        targetSequence.add(zero);

        LinkedList<Integer> sourceSequence = new LinkedList<Integer>();
        sourceSequence.add((Integer) null);
        sourceSequence.add(minusTwo);

        SequencesComparator<Integer> comparator = new SequencesComparator<Integer>(sourceSequence, targetSequence);
        EditScript<Integer> script = comparator.getScript();

        assertEquals(4, script.getModifications());
    }
}
