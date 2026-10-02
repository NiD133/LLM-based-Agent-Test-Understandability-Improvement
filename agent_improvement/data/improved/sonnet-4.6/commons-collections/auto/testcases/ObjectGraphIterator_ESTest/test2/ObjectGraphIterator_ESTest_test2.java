package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.apache.commons.collections4.functors.IdentityPredicate;
import org.apache.commons.collections4.functors.NotNullPredicate;
import org.apache.commons.collections4.functors.PredicateTransformer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ObjectGraphIterator_ESTest_test2 extends ObjectGraphIterator_ESTest_scaffolding {

    /**
     * Verifies that calling remove() after next() on an ObjectGraphIterator that wraps
     * another ObjectGraphIterator throws IllegalStateException. The inner iterator
     * found its element through the root object (not via a sub-iterator), so its
     * lastUsedIterator is null, making remove() invalid on the inner iterator.
     */
    @Test(timeout = 4000)
    public void test2() throws Throwable {
        Integer rootValue = Integer.valueOf(414);
        // Transformer always returns the same constant value, turning any input into rootValue
        Transformer<Object, Integer> constantTransformer = ConstantTransformer.constantTransformer(rootValue);

        // Inner iterator: traverses a single root integer using the constant transformer
        ObjectGraphIterator<Integer> innerIterator = new ObjectGraphIterator<Integer>(rootValue, constantTransformer);
        // Outer iterator: wraps the inner iterator (iterator-of-iterators mode)
        ObjectGraphIterator<Integer> outerIterator = new ObjectGraphIterator<Integer>(innerIterator);

        // Advance to the first (and only) element so that next() has been called
        outerIterator.next();

        // remove() delegates to the inner iterator's remove(), which fails because
        // the inner iterator found the element via its root path (lastUsedIterator == null)
        try {
            outerIterator.remove();
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            //
            // Iterator remove() cannot be called at this time
            //
            verifyException("org.apache.commons.collections4.iterators.ObjectGraphIterator", e);
        }
    }
}
