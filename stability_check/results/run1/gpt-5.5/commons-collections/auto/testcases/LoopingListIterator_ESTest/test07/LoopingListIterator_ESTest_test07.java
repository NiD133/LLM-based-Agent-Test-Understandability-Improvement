package org.apache.commons.collections4.iterators;

import static org.evosuite.runtime.EvoAssertions.*;
import static org.junit.Assert.*;

import java.util.LinkedList;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoopingListIterator_ESTest_test07 extends LoopingListIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        LinkedList<Object> singleElementList = new LinkedList<Object>();
        Object listElement = new Object();
        singleElementList.add(listElement);

        LoopingListIterator<Object> iterator = new LoopingListIterator<Object>(singleElementList);

        int nextElementIndex = iterator.nextIndex();

        assertEquals(0, nextElementIndex);
    }
}
