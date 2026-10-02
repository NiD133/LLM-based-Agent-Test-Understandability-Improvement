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

    /**
     * Compares two sequences and verifies how many edit operations
     * (insert / delete / keep) are needed to turn the first into the second.
     *
     * <p>First sequence:  [null, null, null]
     * <p>Second sequence: [-338, null]
     *
     * <p>Transforming the first sequence into the second requires 3 modifications.
     */
    @Test(timeout = 4000)
    public void test2() throws Throwable {
        // Second sequence: [-338, null]
        LinkedList<Integer> secondSequence = new LinkedList<Integer>();
        Integer value = new Integer((-338));
        secondSequence.add(value);
        secondSequence.add((Integer) null);

        // First sequence: [null, null, null]
        LinkedList<Integer> firstSequence = new LinkedList<Integer>();
        firstSequence.add((Integer) null);
        firstSequence.offerFirst((Integer) null);
        firstSequence.add((Integer) null);

        SequencesComparator<Integer> comparator =
                new SequencesComparator<Integer>(firstSequence, secondSequence);
        EditScript<Integer> editScript = comparator.getScript();

        assertEquals(3, editScript.getModifications());
    }
}
