package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ObjectGraphIterator_ESTest_test5 extends ObjectGraphIterator_ESTest_scaffolding {

    /**
     * Verifies that an ObjectGraphIterator which simply wraps another
     * ObjectGraphIterator yields the element produced by the inner iterator.
     *
     * The inner iterator is built from a single root value (419) and a
     * ConstantTransformer that always maps the root to that same value, so the
     * graph contains exactly one element. The outer iterator is created with the
     * "iterator of iterators" constructor, using the inner iterator as its root.
     * Calling next() on the outer iterator therefore returns 419.
     */
    @Test(timeout = 4000)
    public void next_throughWrappedIterator_returnsRootValue() throws Throwable {
        Integer rootValue = new Integer(419);

        // Transformer that always resolves any input to the root value.
        Transformer<Object, Integer> constantTransformer =
                ConstantTransformer.constantTransformer(rootValue);

        // Inner iterator: traverses a one-element graph rooted at 419.
        ObjectGraphIterator<Integer> innerIterator =
                new ObjectGraphIterator<Integer>(rootValue, constantTransformer);

        // Outer iterator: wraps the inner iterator as its root iterator.
        ObjectGraphIterator<Integer> outerIterator =
                new ObjectGraphIterator<Integer>(innerIterator);

        // Prime the inner iterator's next value (no effect on the outcome below).
        innerIterator.findNext(rootValue);

        Integer result = outerIterator.next();

        assertEquals(419, (int) result);
    }
}
