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
     * Exercises {@link ObjectGraphIterator#findNext(Object)} when the start value
     * is itself an Iterator. In that case findNext should delegate to
     * findNextByIterator and recurse into the supplied iterator without error.
     */
    @Test(timeout = 4000)
    public void findNextWithIteratorValueDelegatesToInnerIterator() throws Throwable {
        // Build a Boolean value via PredicateTransformer: NotNullPredicate returns
        // TRUE because the (non-null) constant transformer instance is not null.
        Predicate<Object> notNull = NotNullPredicate.notNullPredicate();
        PredicateTransformer<Transformer<Boolean, Boolean>> predicateTransformer =
                new PredicateTransformer<Transformer<Boolean, Boolean>>(notNull);
        ConstantTransformer<Boolean, Boolean> constantNullTransformer =
                new ConstantTransformer<Boolean, Boolean>((Boolean) null);
        Boolean rootValue = predicateTransformer.transform(constantNullTransformer);

        // An iterator whose root is the Boolean value and whose transformer always yields null.
        ObjectGraphIterator<Boolean> innerIterator =
                new ObjectGraphIterator<Boolean>(rootValue, constantNullTransformer);

        // An outer iterator that wraps the inner iterator (iterator-of-iterators form).
        ObjectGraphIterator<Object> outerIterator =
                new ObjectGraphIterator<Object>(innerIterator);

        // findNext is given an Iterator value, so it recurses through findNextByIterator.
        outerIterator.findNext(innerIterator);
    }
}
