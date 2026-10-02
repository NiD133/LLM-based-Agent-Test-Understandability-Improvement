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
public class SequencesComparator_ESTest_test0 extends SequencesComparator_ESTest_scaffolding {

    /**
     * Comparing the longer sequence [null, 11, null] against the shorter
     * sequence [null] should yield an edit script that deletes the two
     * extra elements (the boxed 11 and one of the nulls), i.e. 2 modifications.
     */
    @Test(timeout = 4000)
    public void getScriptCountsDeletionsNeededToShrinkSequence() throws Throwable {
        // The shorter target sequence: a single null element.
        LinkedList<Integer> shorterSequence = new LinkedList<Integer>();
        shorterSequence.add((Integer) null);

        // The longer source sequence, built up to [null, 11, null].
        LinkedList<Integer> longerSequence = new LinkedList<Integer>(shorterSequence);
        Integer eleven = new Integer(11);
        longerSequence.add(eleven);
        longerSequence.addAll((Collection<? extends Integer>) shorterSequence);

        SequencesComparator<Integer> comparator =
                new SequencesComparator<Integer>(longerSequence, shorterSequence);
        EditScript<Integer> editScript = comparator.getScript();

        // Two elements must be removed to turn the longer sequence into the shorter one.
        assertEquals(2, editScript.getModifications());
    }
}
