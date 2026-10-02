package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.functors.InstanceofPredicate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoopingListIterator_ESTest_test04 extends LoopingListIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        LinkedList<LinkedList<Object>> linkedList0 = new LinkedList<LinkedList<Object>>();
        LinkedList<Object> linkedList1 = new LinkedList<Object>();
        linkedList0.add(linkedList1);
        LoopingListIterator<LinkedList<Object>> loopingListIterator0 = new LoopingListIterator<LinkedList<Object>>(linkedList0);
        LinkedList<Object> linkedList2 = loopingListIterator0.previous();
        assertTrue(linkedList0.contains(linkedList2));
    }
}
