package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedList;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.functors.InstanceofPredicate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LoopingListIterator_ESTest_test05 extends LoopingListIterator_ESTest_scaffolding {

    /**
     * After consuming the only element of a single-element list via next(),
     * the iterator sits at the physical end of the list. In that state
     * nextIndex() loops back and reports 0 (the start of the list).
     */
    @Test(timeout = 4000)
    public void nextIndexLoopsBackToZeroAtEndOfList() throws Throwable {
        LinkedList<Predicate<Object>> singleElementList = new LinkedList<Predicate<Object>>();
        singleElementList.add(new InstanceofPredicate(Integer.class));

        LoopingListIterator<Predicate<Object>> iterator =
            new LoopingListIterator<Predicate<Object>>(singleElementList);

        iterator.next();

        assertEquals(0, iterator.nextIndex());
    }
}
