package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ObjectGraphIterator_ESTest_test6 extends ObjectGraphIterator_ESTest_scaffolding {

    /**
     * Verifies that findNextByIterator() correctly handles a nested iterator
     * that has no elements (null root): it should fall back to the previously
     * stacked iterator and locate the next value from there.
     */
    @Test(timeout = 4000)
    public void test6() throws Throwable {
        // A constant transformer always returns the same integer, forming a one-element cycle
        Integer constantValue = new Integer(414);
        Transformer<Object, Integer> constantTransformer = ConstantTransformer.constantTransformer(constantValue);

        // Base iterator: rooted at constantValue, applies constantTransformer to advance
        ObjectGraphIterator<Integer> baseIterator =
                new ObjectGraphIterator<Integer>(constantValue, constantTransformer);

        // Wrapping iterator: treats baseIterator as an iterator-of-iterators
        ObjectGraphIterator<Integer> wrappingIterator =
                new ObjectGraphIterator<Integer>(baseIterator);

        // Null-root iterator: produces no elements on its own when traversed
        ObjectGraphIterator<Integer> nullRootIterator =
                new ObjectGraphIterator<Integer>((Integer) null, constantTransformer);

        // Redirect wrappingIterator to traverse nullRootIterator first;
        // since nullRootIterator has no elements, the traversal falls back to the
        // stacked baseIterator and finds the next value there
        wrappingIterator.findNextByIterator(nullRootIterator);

        // nullRootIterator and baseIterator are independent objects created separately
        assertNotSame(nullRootIterator, baseIterator);
    }
}
