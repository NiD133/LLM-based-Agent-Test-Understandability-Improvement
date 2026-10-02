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
public class LoopingListIterator_ESTest_test07 extends LoopingListIterator_ESTest_scaffolding {

    /**
     * On a freshly created iterator positioned before the first element,
     * nextIndex() should report 0 (the index of the element next() would return).
     */
    @Test(timeout = 4000)
    public void nextIndexAtStartReturnsZero() throws Throwable {
        LinkedList<Object> singleElementList = new LinkedList<Object>();
        singleElementList.add(new Object());

        LoopingListIterator<Object> iterator = new LoopingListIterator<Object>(singleElementList);

        assertEquals(0, iterator.nextIndex());
    }
}
