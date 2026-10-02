package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedList;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoopingListIterator_ESTest_test14 extends LoopingListIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_hasNext_returnsTrueWhenListHasOneElement() throws Throwable {
        LinkedList<Object> innerList = new LinkedList<Object>();
        LinkedList<LinkedList<Object>> outerList = new LinkedList<LinkedList<Object>>();
        outerList.addFirst(innerList);

        LoopingListIterator<LinkedList<Object>> iterator = new LoopingListIterator<LinkedList<Object>>(outerList);

        assertTrue(iterator.hasNext());
    }
}
