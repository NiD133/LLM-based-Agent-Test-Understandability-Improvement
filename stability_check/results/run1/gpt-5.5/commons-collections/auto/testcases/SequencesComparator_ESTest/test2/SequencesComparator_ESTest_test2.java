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
public class SequencesComparator_ESTest_test2 extends SequencesComparator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        LinkedList<Integer> expectedSequence = new LinkedList<Integer>();
        LinkedList<Integer> actualSequence = new LinkedList<Integer>();
        Integer deletedValue = new Integer((-338));

        expectedSequence.add(deletedValue);
        actualSequence.add((Integer) null);
        actualSequence.offerFirst((Integer) null);
        expectedSequence.add((Integer) null);
        actualSequence.add((Integer) null);

        SequencesComparator<Integer> comparator =
                new SequencesComparator<Integer>(actualSequence, expectedSequence);
        EditScript<Integer> editScript = comparator.getScript();

        assertEquals(3, editScript.getModifications());
    }
}
