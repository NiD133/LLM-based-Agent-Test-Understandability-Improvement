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

    /**
     * Setting a predicate on a freshly constructed FilterListIterator should not
     * advance its position: nextIndex() must still report the initial value of 0.
     */
    @Test(timeout = 4000)
    public void settingPredicateLeavesNextIndexAtZero() throws Throwable {
        FilterListIterator<Transformer<Object, Object>> filterListIterator =
                new FilterListIterator<Transformer<Object, Object>>();

        EqualPredicate<Object> equalToObject =
                new EqualPredicate<Object>(new Object(), DefaultEquator.defaultEquator());
        filterListIterator.setPredicate(equalToObject);

        assertEquals(0, filterListIterator.nextIndex());
    }
}
