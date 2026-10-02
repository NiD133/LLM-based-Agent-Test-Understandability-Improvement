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
     * Compares two sequences that share no common, order-preserving subsequence
     * beyond a single trailing {@code null}, and verifies the resulting edit
     * script reports the expected number of modifications.
     *
     * <p>First sequence  (source): [null, null, null]
     * <br>Second sequence (target): [-338, null]</p>
     *
     * <p>Transforming the source into the target requires 3 modifications
     * (the shared trailing {@code null} is kept, everything else is
     * deleted/inserted).</p>
     */
    @Test(timeout = 4000)
    public void getScriptCountsModificationsBetweenNullHeavySequences() throws Throwable {
        // Source sequence: three null elements.
        LinkedList<Integer> sourceSequence = new LinkedList<Integer>();
        sourceSequence.add((Integer) null);
        sourceSequence.offerFirst((Integer) null);
        sourceSequence.add((Integer) null);

        // Target sequence: a single value followed by a null.
        LinkedList<Integer> targetSequence = new LinkedList<Integer>();
        targetSequence.add(Integer.valueOf(-338));
        targetSequence.add((Integer) null);

        SequencesComparator<Integer> comparator =
                new SequencesComparator<Integer>(sourceSequence, targetSequence);
        EditScript<Integer> editScript = comparator.getScript();

        assertEquals(3, editScript.getModifications());
    }
}
