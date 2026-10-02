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
        LinkedList<Object> singleElementList = new LinkedList<Object>();
        Object listElement = new Object();
        singleElementList.add(listElement);

        LoopingListIterator<Object> iterator = new LoopingListIterator<Object>(singleElementList);

        // At the start of a one-element looping list, previousIndex wraps to the only element.
        int previousIndex = iterator.previousIndex();
        assertEquals(0, previousIndex);
    }
}
