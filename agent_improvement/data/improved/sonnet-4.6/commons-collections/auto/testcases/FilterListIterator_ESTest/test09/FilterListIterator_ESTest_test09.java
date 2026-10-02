package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.apache.commons.collections4.Closure;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.functors.DefaultEquator;
import org.apache.commons.collections4.functors.EqualPredicate;
import org.apache.commons.collections4.functors.InstanceofPredicate;
import org.apache.commons.collections4.functors.NullIsFalsePredicate;
import org.apache.commons.collections4.functors.UniquePredicate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FilterListIterator_ESTest_test09 extends FilterListIterator_ESTest_scaffolding {

    /**
     * Tests that constructing a FilterListIterator with a null predicate
     * stores it as-is, so getPredicate() returns null rather than substituting
     * a default predicate.
     */
    @Test(timeout = 4000)
    public void test_constructorWithNullPredicate_getPredicateReturnsNull() throws Throwable {
        FilterListIterator<Integer> filterListIterator0 = new FilterListIterator<Integer>((Predicate<? super Integer>) null);
        Predicate<? super Integer> predicate0 = filterListIterator0.getPredicate();
        assertNull(predicate0);
    }
}
