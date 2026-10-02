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
     * The edit script needs 3 modifications: delete two nulls and insert -338.
     */
    @Test(timeout = 4000)
    public void test2() throws Throwable {
        // Build sequence1: [null, null, null]
        LinkedList<Integer> threeNulls = new LinkedList<Integer>();
        threeNulls.add((Integer) null);
        threeNulls.offerFirst((Integer) null); // inserts at front → [null, null]
        threeNulls.add((Integer) null);        // appends → [null, null, null]

        // Build sequence2: [-338, null]
        LinkedList<Integer> negativeAndNull = new LinkedList<Integer>();
        Integer negativeValue = new Integer((-338));
        negativeAndNull.add(negativeValue);
        negativeAndNull.add((Integer) null);

        SequencesComparator<Integer> comparator = new SequencesComparator<Integer>(threeNulls, negativeAndNull);
        EditScript<Integer> editScript = comparator.getScript();

        // Transforming [null, null, null] → [-338, null] requires 3 modifications
        assertEquals(3, editScript.getModifications());
    }
}
