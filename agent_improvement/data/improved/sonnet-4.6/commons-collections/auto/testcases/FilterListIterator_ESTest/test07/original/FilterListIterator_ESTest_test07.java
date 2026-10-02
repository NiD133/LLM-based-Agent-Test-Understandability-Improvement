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
public class FilterListIterator_ESTest_test07 extends FilterListIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        LinkedList<Object> linkedList0 = new LinkedList<Object>();
        Object object0 = new Object();
        linkedList0.push(object0);
        ListIterator<Object> listIterator0 = linkedList0.listIterator();
        DefaultEquator<Object> defaultEquator0 = DefaultEquator.defaultEquator();
        EqualPredicate<Object> equalPredicate0 = new EqualPredicate<Object>(object0, defaultEquator0);
        FilterListIterator<Object> filterListIterator0 = new FilterListIterator<Object>(listIterator0, equalPredicate0);
        filterListIterator0.next();
        filterListIterator0.hasPrevious();
        boolean boolean0 = filterListIterator0.hasPrevious();
        assertEquals(0, filterListIterator0.previousIndex());
        assertTrue(boolean0);
    }
}
