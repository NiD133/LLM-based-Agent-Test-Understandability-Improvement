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
public class SequencesComparator_ESTest_test4 extends SequencesComparator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test4() throws Throwable {
        LinkedList<Integer> linkedList0 = new LinkedList<Integer>();
        linkedList0.add((Integer) null);
        LinkedList<Integer> linkedList1 = new LinkedList<Integer>(linkedList0);
        Integer integer0 = new Integer((-363));
        linkedList1.add(integer0);
        linkedList1.add(integer0);
        SequencesComparator<Integer> sequencesComparator0 = new SequencesComparator<Integer>(linkedList0, linkedList1);
        EditScript<Integer> editScript0 = sequencesComparator0.getScript();
        assertEquals(2, editScript0.getModifications());
    }
}
