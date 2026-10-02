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
public class LoopingListIterator_ESTest_test05 extends LoopingListIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        LinkedList<InstanceofPredicate> singlePredicateSource = new LinkedList<InstanceofPredicate>();
        Class<Integer> integerClass = Integer.class;
        InstanceofPredicate integerPredicate = new InstanceofPredicate(integerClass);
        singlePredicateSource.offerFirst(integerPredicate);

        LinkedList<Predicate<Object>> predicates = new LinkedList<Predicate<Object>>(singlePredicateSource);
        LoopingListIterator<Predicate<Object>> iterator = new LoopingListIterator<Predicate<Object>>(predicates);

        iterator.next();
        int wrappedNextIndex = iterator.nextIndex();

        assertEquals(0, wrappedNextIndex);
    }
}
