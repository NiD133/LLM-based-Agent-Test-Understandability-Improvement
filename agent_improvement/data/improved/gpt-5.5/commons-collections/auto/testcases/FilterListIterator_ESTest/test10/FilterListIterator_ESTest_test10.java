package org.apache.commons.collections4.iterators;

import static org.evosuite.runtime.EvoAssertions.*;
import static org.junit.Assert.*;

import java.util.ListIterator;

import org.apache.commons.collections4.functors.NullIsFalsePredicate;
import org.apache.commons.collections4.functors.UniquePredicate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FilterListIterator_ESTest_test10 extends FilterListIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        UniquePredicate<Object> uniquePredicate = new UniquePredicate<Object>();
        NullIsFalsePredicate<Object> nonNullUniquePredicate = new NullIsFalsePredicate<Object>(uniquePredicate);
        FilterListIterator<Object> iteratorWithPredicateOnly = new FilterListIterator<Object>(nonNullUniquePredicate);

        ListIterator<?> backingIterator = iteratorWithPredicateOnly.getListIterator();

        assertNull(backingIterator);
    }
}
