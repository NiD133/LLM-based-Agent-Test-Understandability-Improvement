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
     * Compares a sequence of three nulls against a two-element sequence [-338, null].
     * The edit script should require 3 modifications: one substitution (null→-338)
     * and one deletion of the extra null.
     */
    @Test(timeout = 4000)
    public void test2() throws Throwable {
        // Sequence 2: [-338, null]
        LinkedList<Integer> sequenceWithValue = new LinkedList<Integer>();
        Integer negativeValue = new Integer((-338));
        sequenceWithValue.add(negativeValue);
        sequenceWithValue.add((Integer) null);

        // Sequence 1: [null, null, null]  (built via add + offerFirst + add)
        LinkedList<Integer> threeNullSequence = new LinkedList<Integer>();
        threeNullSequence.add((Integer) null);
        threeNullSequence.offerFirst((Integer) null);
        threeNullSequence.add((Integer) null);

        // Compare [null, null, null] (first) against [-338, null] (second)
        SequencesComparator<Integer> comparator = new SequencesComparator<Integer>(threeNullSequence, sequenceWithValue);
        EditScript<Integer> editScript = comparator.getScript();

        assertEquals(3, editScript.getModifications());
    }
}
