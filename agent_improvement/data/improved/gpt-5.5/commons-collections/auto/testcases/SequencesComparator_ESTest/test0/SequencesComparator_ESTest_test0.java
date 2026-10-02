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

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        LinkedList<Integer> expectedSequence = new LinkedList<Integer>();
        expectedSequence.add((Integer) null);

        LinkedList<Integer> actualSequence = new LinkedList<Integer>(expectedSequence);
        Integer insertedValue = new Integer(11);
        actualSequence.add(insertedValue);
        actualSequence.addAll((Collection<? extends Integer>) expectedSequence);

        SequencesComparator<Integer> comparator = new SequencesComparator<Integer>(actualSequence, expectedSequence);
        EditScript<Integer> editScript = comparator.getScript();

        assertEquals(2, editScript.getModifications());
    }
}
