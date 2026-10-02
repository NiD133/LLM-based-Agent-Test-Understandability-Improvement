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
public class ObjectGraphIterator_ESTest_test1 extends ObjectGraphIterator_ESTest_scaffolding {

    /**
     * Verifies that findNextByIterator correctly handles traversal when an
     * ObjectGraphIterator (wrapping a single Integer root with no transformer)
     * is passed as a sub-iterator to another ObjectGraphIterator.
     */
    @Test(timeout = 4000)
    public void test1() throws Throwable {
        Integer rootValue = new Integer(2928);

        // Outer iterator traverses an Object graph with no transformer applied
        ObjectGraphIterator<Object> outerIterator =
                new ObjectGraphIterator<Object>(rootValue, (Transformer<? super Object, ?>) null);

        // Inner iterator wraps the same root value with no transformer;
        // it acts as a sub-iterator fed into the outer iterator
        ObjectGraphIterator<Integer> innerIterator =
                new ObjectGraphIterator<Integer>(rootValue, (Transformer<? super Integer, ? extends Integer>) null);

        // Advance the outer iterator by traversing elements via the inner iterator
        outerIterator.findNextByIterator(innerIterator);
    }
}
