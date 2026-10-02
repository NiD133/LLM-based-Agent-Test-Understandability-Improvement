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
public class ObjectGraphIterator_ESTest_test5 extends ObjectGraphIterator_ESTest_scaffolding {

    /**
     * Verifies that a wrapping ObjectGraphIterator (created from another iterator)
     * correctly retrieves the value pre-primed by a direct findNext() call on the
     * inner iterator.
     *
     * Setup:
     *   - innerIterator: rooted at 419, with a ConstantTransformer that always returns 419
     *   - wrappingIterator: wraps innerIterator using the Iterator constructor (no transformer)
     *
     * When findNext(419) is called directly on innerIterator, it sets the inner
     * iterator's currentValue to 419 and marks hasNext=true. Calling next() on the
     * wrapping iterator then pulls that pre-primed value through, returning 419.
     */
    @Test(timeout = 4000)
    public void test5() throws Throwable {
        Integer rootValue = Integer.valueOf(419);

        // A transformer that always returns the same constant value (419),
        // regardless of what is passed to it.
        Transformer<Object, Integer> constantTransformer = ConstantTransformer.constantTransformer(rootValue);

        // innerIterator uses rootValue as its root and constantTransformer to resolve nodes.
        ObjectGraphIterator<Integer> innerIterator = new ObjectGraphIterator<Integer>(rootValue, constantTransformer);

        // wrappingIterator treats innerIterator as its source; it has no transformer of its own.
        ObjectGraphIterator<Integer> wrappingIterator = new ObjectGraphIterator<Integer>(innerIterator);

        // Pre-prime innerIterator by directly calling findNext, which sets its
        // currentValue to rootValue (419) and marks hasNext=true.
        innerIterator.findNext(rootValue);

        // wrappingIterator.next() delegates to innerIterator and retrieves the pre-primed value.
        Integer retrievedValue = wrappingIterator.next();
        assertEquals(419, (int) retrievedValue);
    }
}
