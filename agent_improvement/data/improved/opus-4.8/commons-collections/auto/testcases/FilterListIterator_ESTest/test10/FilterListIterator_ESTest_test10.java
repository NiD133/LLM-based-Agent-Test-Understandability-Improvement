package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ListIterator;
import org.apache.commons.collections4.functors.NullIsFalsePredicate;
import org.apache.commons.collections4.functors.UniquePredicate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FilterListIterator_ESTest_test10 extends FilterListIterator_ESTest_scaffolding {

    /**
     * When a FilterListIterator is built with the predicate-only constructor,
     * no underlying ListIterator has been supplied yet, so getListIterator()
     * should return null.
     */
    @Test(timeout = 4000)
    public void getListIteratorReturnsNullWhenOnlyPredicateProvided() throws Throwable {
        NullIsFalsePredicate<Object> predicate = new NullIsFalsePredicate<Object>(new UniquePredicate<Object>());
        FilterListIterator<Object> filterListIterator = new FilterListIterator<Object>(predicate);

        ListIterator<?> underlyingIterator = filterListIterator.getListIterator();

        assertNull(underlyingIterator);
    }
}
