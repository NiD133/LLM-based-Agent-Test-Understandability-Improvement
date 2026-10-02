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
public class ObjectGraphIterator_ESTest_test7 extends ObjectGraphIterator_ESTest_scaffolding {

    /**
     * Verifies that findNext() correctly handles an Iterator argument by
     * delegating to the iterator-based traversal path.
     *
     * Setup:
     *  - A NotNullPredicate is wrapped in a PredicateTransformer so that
     *    transforming any non-null object yields Boolean.TRUE.
     *  - A ConstantTransformer that always returns null is used as both the
     *    subject of the predicate check and as the graph transformer.
     *  - The predicate check on the non-null ConstantTransformer yields TRUE,
     *    which becomes the root element of the inner iterator.
     *  - An outer ObjectGraphIterator wraps the inner one, and findNext() is
     *    called with the inner iterator as its argument, exercising the code
     *    path that recurses into an iterator-typed value.
     */
    @Test(timeout = 4000)
    public void test7() throws Throwable {
        // A predicate that returns true for any non-null input
        Predicate<Object> notNullPredicate = NotNullPredicate.notNullPredicate();

        // Wrap the predicate in a transformer so transform(x) == predicate.evaluate(x)
        PredicateTransformer<Transformer<Boolean, Boolean>> predicateAsTransformer =
                new PredicateTransformer<Transformer<Boolean, Boolean>>(notNullPredicate);

        // A graph transformer that collapses every element to null
        ConstantTransformer<Boolean, Boolean> alwaysNullTransformer =
                new ConstantTransformer<Boolean, Boolean>((Boolean) null);

        // alwaysNullTransformer is not null, so the predicate returns TRUE
        Boolean rootValue = predicateAsTransformer.transform(alwaysNullTransformer);

        // Inner iterator: root=TRUE, transformer always maps to null
        ObjectGraphIterator<Boolean> innerIterator =
                new ObjectGraphIterator<Boolean>(rootValue, alwaysNullTransformer);

        // Outer iterator that traverses the inner iterator as its root
        ObjectGraphIterator<Object> outerIterator =
                new ObjectGraphIterator<Object>(innerIterator);

        // Calling findNext() with an Iterator argument triggers the iterator
        // traversal branch inside findNext(), which delegates to findNextByIterator()
        outerIterator.findNext(innerIterator);
    }
}
