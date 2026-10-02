package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
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
     * Verifies that a FilterListIterator constructed with only a predicate
     * (no underlying ListIterator) returns null from getListIterator().
     */
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        // Build a predicate that rejects null values and tracks unique elements
        UniquePredicate<Object> uniquePredicate0 = new UniquePredicate<Object>();
        NullIsFalsePredicate<Object> nullIsFalsePredicate0 = new NullIsFalsePredicate<Object>(uniquePredicate0);

        // Construct a FilterListIterator with only a predicate — no backing ListIterator
        FilterListIterator<Object> filterListIterator0 = new FilterListIterator<Object>(nullIsFalsePredicate0);

        // When no ListIterator has been set, getListIterator() should return null
        ListIterator<?> listIterator0 = filterListIterator0.getListIterator();
        assertNull(listIterator0);
    }
}
