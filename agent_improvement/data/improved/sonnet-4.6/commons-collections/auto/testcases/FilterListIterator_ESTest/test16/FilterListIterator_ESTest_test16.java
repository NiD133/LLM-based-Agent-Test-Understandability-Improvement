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
public class FilterListIterator_ESTest_test16 extends FilterListIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test16_previousIndex_returnsNegativeOne_atInitialPosition() throws Throwable {
        // A freshly constructed FilterListIterator starts with nextIndex = 0,
        // so previousIndex() (which returns nextIndex - 1) should return -1,
        // matching the ListIterator contract for an iterator before its first element.
        FilterListIterator<Integer> filterListIterator0 = new FilterListIterator<Integer>();

        // Set the iterator's underlying delegate to itself; previousIndex() does not
        // consult the delegate, so this unusual self-referential setup does not affect the result.
        filterListIterator0.setListIterator(filterListIterator0);

        assertEquals((-1), filterListIterator0.previousIndex());
    }
}
