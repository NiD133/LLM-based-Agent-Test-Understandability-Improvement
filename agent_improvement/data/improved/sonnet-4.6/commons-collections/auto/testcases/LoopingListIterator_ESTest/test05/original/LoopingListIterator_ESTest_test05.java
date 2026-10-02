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
public class LoopingListIterator_ESTest_test05 extends LoopingListIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        LinkedList<InstanceofPredicate> linkedList0 = new LinkedList<InstanceofPredicate>();
        Class<Integer> class0 = Integer.class;
        InstanceofPredicate instanceofPredicate0 = new InstanceofPredicate(class0);
        linkedList0.offerFirst(instanceofPredicate0);
        LinkedList<Predicate<Object>> linkedList1 = new LinkedList<Predicate<Object>>(linkedList0);
        LoopingListIterator<Predicate<Object>> loopingListIterator0 = new LoopingListIterator<Predicate<Object>>(linkedList1);
        loopingListIterator0.next();
        int int0 = loopingListIterator0.nextIndex();
        assertEquals(0, int0);
    }
}
