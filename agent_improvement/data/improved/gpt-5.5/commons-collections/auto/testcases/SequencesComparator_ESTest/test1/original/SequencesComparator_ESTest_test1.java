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
public class SequencesComparator_ESTest_test1 extends SequencesComparator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        LinkedList<Integer> linkedList0 = new LinkedList<Integer>();
        Integer integer0 = new Integer(0);
        linkedList0.add(integer0);
        Integer integer1 = new Integer((-2));
        linkedList0.add(integer1);
        linkedList0.add(integer1);
        linkedList0.add(integer0);
        LinkedList<Integer> linkedList1 = new LinkedList<Integer>();
        linkedList1.add((Integer) null);
        linkedList1.add(integer1);
        SequencesComparator<Integer> sequencesComparator0 = new SequencesComparator<Integer>(linkedList1, linkedList0);
        EditScript<Integer> editScript0 = sequencesComparator0.getScript();
        assertEquals(4, editScript0.getModifications());
    }
}
