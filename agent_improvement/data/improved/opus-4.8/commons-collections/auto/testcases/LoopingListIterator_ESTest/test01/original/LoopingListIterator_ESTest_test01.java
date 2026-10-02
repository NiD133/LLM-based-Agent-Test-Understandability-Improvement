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
public class LoopingListIterator_ESTest_test01 extends LoopingListIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        LinkedList<Object> linkedList0 = new LinkedList<Object>();
        LoopingListIterator<Object> loopingListIterator0 = new LoopingListIterator<Object>(linkedList0);
        // Undeclared exception!
        try {
            loopingListIterator0.previousIndex();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            //
            // There are no elements for this iterator to loop on
            //
            verifyException("org.apache.commons.collections4.iterators.LoopingListIterator", e);
        }
    }
}
