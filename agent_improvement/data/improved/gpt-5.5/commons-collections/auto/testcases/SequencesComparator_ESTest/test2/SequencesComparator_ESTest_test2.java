package org.apache.commons.collections4.sequence;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedList;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SequencesComparator_ESTest_test2 extends SequencesComparator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        LinkedList<Integer> targetSequence = new LinkedList<Integer>();
        LinkedList<Integer> sourceSequence = new LinkedList<Integer>();

        Integer negativeValue = new Integer((-338));
        targetSequence.add(negativeValue);
        sourceSequence.add((Integer) null);
        sourceSequence.offerFirst((Integer) null);
        targetSequence.add((Integer) null);
        sourceSequence.add((Integer) null);

        SequencesComparator<Integer> comparator = new SequencesComparator<Integer>(sourceSequence, targetSequence);
        EditScript<Integer> editScript = comparator.getScript();

        assertEquals(3, editScript.getModifications());
    }
}
