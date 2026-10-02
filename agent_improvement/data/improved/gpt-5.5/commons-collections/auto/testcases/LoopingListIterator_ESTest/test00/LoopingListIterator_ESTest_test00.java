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
public class LoopingListIterator_ESTest_test00 extends LoopingListIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        LinkedList<InstanceofPredicate> predicates = new LinkedList<InstanceofPredicate>();
        LoopingListIterator<InstanceofPredicate> iterator = new LoopingListIterator<InstanceofPredicate>(predicates);

        Class<Object> acceptedType = Object.class;
        InstanceofPredicate objectPredicate = new InstanceofPredicate(acceptedType);
        iterator.add(objectPredicate);

        int previousIndexAfterAddingFirstElement = iterator.previousIndex();
        assertEquals(0, previousIndexAfterAddingFirstElement);
    }
}
