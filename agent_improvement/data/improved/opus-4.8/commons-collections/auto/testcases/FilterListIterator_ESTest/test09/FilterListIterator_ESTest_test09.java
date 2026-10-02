package org.apache.commons.collections4.iterators;

import static org.junit.Assert.assertNull;

import org.apache.commons.collections4.Predicate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FilterListIterator_ESTest_test09 extends FilterListIterator_ESTest_scaffolding {

    /**
     * When a FilterListIterator is built with the predicate-only constructor and
     * given a {@code null} predicate, {@link FilterListIterator#getPredicate()}
     * should return that same {@code null} predicate back.
     */
    @Test(timeout = 4000)
    public void getPredicateReturnsNullWhenConstructedWithNullPredicate() throws Throwable {
        Predicate<? super Integer> nullPredicate = null;
        FilterListIterator<Integer> filterListIterator =
                new FilterListIterator<Integer>(nullPredicate);

        Predicate<? super Integer> returnedPredicate = filterListIterator.getPredicate();

        assertNull(returnedPredicate);
    }
}
