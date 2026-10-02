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
public class ObjectGraphIterator_ESTest_test2 extends ObjectGraphIterator_ESTest_scaffolding {

    /**
     * Verifies that calling remove() throws IllegalStateException when the
     * iterator that actually produced the last element never tracked a
     * removable underlying iterator.
     *
     * Setup: an inner ObjectGraphIterator built from a single root value plus a
     * constant transformer (so it yields the value without ever delegating to a
     * real underlying iterator). That inner iterator is then wrapped by an outer
     * ObjectGraphIterator-of-iterators.
     *
     * After next() on the outer iterator, the outer iterator delegates remove()
     * to the inner iterator, whose last-used iterator is still null. Therefore
     * the IllegalStateException ("Iterator remove() cannot be called at this
     * time") originates from ObjectGraphIterator itself.
     */
    @Test(timeout = 4000)
    public void remove_afterNext_whenNoUnderlyingIterator_throwsIllegalState() throws Throwable {
        Integer rootValue = new Integer(414);

        // A transformer that always returns the same value, so the inner
        // iterator never recurses into a real underlying iterator.
        Transformer<Object, Integer> constantTransformer =
                ConstantTransformer.constantTransformer(rootValue);

        // Inner iterator: produces rootValue directly from the transformer.
        ObjectGraphIterator<Integer> innerIterator =
                new ObjectGraphIterator<Integer>(rootValue, constantTransformer);

        // Outer iterator: treats the inner iterator as its root iterator.
        ObjectGraphIterator<Integer> outerIterator =
                new ObjectGraphIterator<Integer>(innerIterator);

        // Consume the single available element.
        outerIterator.next();

        // remove() is not legal here: the inner iterator (used to produce the
        // element) never recorded a removable underlying iterator.
        try {
            outerIterator.remove();
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            // Iterator remove() cannot be called at this time
            verifyException("org.apache.commons.collections4.iterators.ObjectGraphIterator", e);
        }
    }
}
