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
     * Compares [null, null, null] (sequence1) against [-338, null] (sequence2).
     * The edit script requires 3 modifications: two deletions and one insertion.
     */
    @Test(timeout = 4000)
    public void test2() throws Throwable {
        // Build sequence2: [-338, null]
        LinkedList<Integer> sequenceWithElementAndNull = new LinkedList<Integer>();
        sequenceWithElementAndNull.add(-338);
        sequenceWithElementAndNull.add((Integer) null);

        // Build sequence1: [null, null, null]
        // Start with [null], prepend null to get [null, null], then append null → [null, null, null]
        LinkedList<Integer> sequenceOfNulls = new LinkedList<Integer>();
        sequenceOfNulls.add((Integer) null);
        sequenceOfNulls.offerFirst((Integer) null);
        sequenceOfNulls.add((Integer) null);

        SequencesComparator<Integer> comparator =
                new SequencesComparator<Integer>(sequenceOfNulls, sequenceWithElementAndNull);
        EditScript<Integer> editScript = comparator.getScript();

        assertEquals(3, editScript.getModifications());
    }
}
