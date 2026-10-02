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
     * A freshly created iterator over a single-element list is positioned at the
     * start, so nextIndex() should report the first index, 0.
     */
    @Test(timeout = 4000)
    public void nextIndexOnFreshIteratorReturnsZero() throws Throwable {
        LinkedList<Object> singleElementList = new LinkedList<Object>();
        singleElementList.add(new Object());
        LoopingListIterator<Object> iterator = new LoopingListIterator<Object>(singleElementList);

        int nextIndex = iterator.nextIndex();

        assertEquals(0, nextIndex);
    }
}
