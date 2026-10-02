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
     * Comparing two differing sequences should report the number of edit
     * commands (insert/delete/keep) needed to transform the first into the
     * second.
     *
     * <p>Here the first sequence is {@code [null, null, null]} and the second
     * is {@code [-338, null]}; transforming one into the other requires three
     * modifications.</p>
     */
    @Test(timeout = 4000)
    public void test2() throws Throwable {
        LinkedList<Integer> secondSequence = new LinkedList<Integer>();
        secondSequence.add(Integer.valueOf(-338));
        secondSequence.add(null);

        LinkedList<Integer> firstSequence = new LinkedList<Integer>();
        firstSequence.add(null);
        firstSequence.offerFirst(null);
        firstSequence.add(null);

        SequencesComparator<Integer> comparator =
                new SequencesComparator<Integer>(firstSequence, secondSequence);
        EditScript<Integer> editScript = comparator.getScript();

        assertEquals(3, editScript.getModifications());
    }
}
