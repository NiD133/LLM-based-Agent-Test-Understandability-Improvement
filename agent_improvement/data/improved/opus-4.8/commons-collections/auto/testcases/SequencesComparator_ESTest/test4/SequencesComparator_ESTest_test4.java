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

    /**
     * When the second sequence extends the first by appending two elements,
     * the edit script should consist of exactly those two appends, i.e. two
     * modifications.
     */
    @Test(timeout = 4000)
    public void getScriptCountsTwoAppendedElementsAsTwoModifications() throws Throwable {
        // First sequence holds a single null element.
        LinkedList<Integer> firstSequence = new LinkedList<Integer>();
        firstSequence.add((Integer) null);

        // Second sequence starts as a copy of the first, then appends the
        // same value twice, so it is two elements longer.
        LinkedList<Integer> secondSequence = new LinkedList<Integer>(firstSequence);
        Integer appendedValue = Integer.valueOf(-363);
        secondSequence.add(appendedValue);
        secondSequence.add(appendedValue);

        SequencesComparator<Integer> comparator =
                new SequencesComparator<Integer>(firstSequence, secondSequence);
        EditScript<Integer> editScript = comparator.getScript();

        // The two extra elements account for the two modifications.
        assertEquals(2, editScript.getModifications());
    }
}
