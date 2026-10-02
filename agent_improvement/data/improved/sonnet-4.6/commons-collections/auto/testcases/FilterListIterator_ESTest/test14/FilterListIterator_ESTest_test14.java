package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.functors.DefaultEquator;
import org.apache.commons.collections4.functors.EqualPredicate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FilterListIterator_ESTest_test14 extends FilterListIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        // A FilterListIterator with no underlying list iterator; nextIndex starts at 0
        FilterListIterator<Transformer<Object, Object>> filterIterator =
                new FilterListIterator<Transformer<Object, Object>>();

        Object referenceObject = new Object();
        DefaultEquator<Object> equator = DefaultEquator.defaultEquator();
        EqualPredicate<Object> equalityPredicate = new EqualPredicate<Object>(referenceObject, equator);

        filterIterator.setPredicate(equalityPredicate);

        assertEquals(0, filterIterator.nextIndex());
    }
}
