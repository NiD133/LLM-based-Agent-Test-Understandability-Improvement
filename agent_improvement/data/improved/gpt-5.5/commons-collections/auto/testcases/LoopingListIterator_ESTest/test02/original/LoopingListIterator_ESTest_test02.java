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
public class LoopingListIterator_ESTest_test02 extends LoopingListIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        LinkedList<Object> linkedList0 = new LinkedList<Object>();
        Object object0 = new Object();
        linkedList0.add(object0);
        LoopingListIterator<Object> loopingListIterator0 = new LoopingListIterator<Object>(linkedList0);
        int int0 = loopingListIterator0.previousIndex();
        assertEquals(0, int0);
    }
}
