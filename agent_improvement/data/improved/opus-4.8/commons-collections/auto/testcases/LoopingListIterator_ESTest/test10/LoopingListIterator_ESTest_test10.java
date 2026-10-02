package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedList;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoopingListIterator_ESTest_test10 extends LoopingListIterator_ESTest_scaffolding {

    /**
     * Verifies that, after advancing past the only element and replacing it via
     * set(), the iterator still reports that more elements are available. Because
     * LoopingListIterator loops around a non-empty list, hasNext() stays true as
     * long as the underlying list keeps at least one element.
     */
    @Test(timeout = 4000)
    public void testHasNextRemainsTrueAfterSetOnSingleElementList() throws Throwable {
        LinkedList<Integer> singleElementList = new LinkedList<Integer>();
        singleElementList.add(Integer.valueOf(2024));

        LoopingListIterator<Integer> loopingIterator =
            new LoopingListIterator<Integer>(singleElementList);

        Integer returnedElement = loopingIterator.next();
        loopingIterator.set(returnedElement);

        assertTrue(loopingIterator.hasNext());
    }
}
