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
public class SequencesComparator_ESTest_test4 extends SequencesComparator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test4() throws Throwable {
        LinkedList<Integer> originalSequence = new LinkedList<Integer>();
        originalSequence.add((Integer) null);

        LinkedList<Integer> expandedSequence = new LinkedList<Integer>(originalSequence);
        Integer repeatedValue = new Integer((-363));
        expandedSequence.add(repeatedValue);
        expandedSequence.add(repeatedValue);

        SequencesComparator<Integer> comparator = new SequencesComparator<Integer>(originalSequence, expandedSequence);
        EditScript<Integer> editScript = comparator.getScript();

        assertEquals(2, editScript.getModifications());
    }
}
