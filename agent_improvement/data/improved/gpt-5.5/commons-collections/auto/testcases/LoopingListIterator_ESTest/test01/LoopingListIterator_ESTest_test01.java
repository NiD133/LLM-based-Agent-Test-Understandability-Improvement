package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoopingListIterator_ESTest_test01 extends LoopingListIterator_ESTest_scaffolding {

    private static final String LOOPING_LIST_ITERATOR_CLASS =
            "org.apache.commons.collections4.iterators.LoopingListIterator";

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        LinkedList<Object> emptyList = new LinkedList<Object>();
        LoopingListIterator<Object> iterator = new LoopingListIterator<Object>(emptyList);

        try {
            iterator.previousIndex();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            verifyException(LOOPING_LIST_ITERATOR_CLASS, e);
        }
    }
}
