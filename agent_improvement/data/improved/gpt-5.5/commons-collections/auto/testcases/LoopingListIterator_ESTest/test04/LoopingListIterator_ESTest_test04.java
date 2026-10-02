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
        LinkedList<LinkedList<Object>> backingList = new LinkedList<LinkedList<Object>>();
        LinkedList<Object> onlyElement = new LinkedList<Object>();
        backingList.add(onlyElement);

        LoopingListIterator<LinkedList<Object>> iterator = new LoopingListIterator<LinkedList<Object>>(backingList);

        LinkedList<Object> elementReturnedFromInitialPrevious = iterator.previous();

        assertTrue(backingList.contains(elementReturnedFromInitialPrevious));
    }
}
