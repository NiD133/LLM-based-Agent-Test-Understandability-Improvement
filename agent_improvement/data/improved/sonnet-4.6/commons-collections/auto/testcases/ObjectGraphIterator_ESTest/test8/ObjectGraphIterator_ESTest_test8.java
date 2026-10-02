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
public class ObjectGraphIterator_ESTest_test8 extends ObjectGraphIterator_ESTest_scaffolding {

    /**
     * Verifies that an ObjectGraphIterator constructed from a plain root object
     * (not an Iterator) with a null transformer can itself be passed as the root
     * of a second ObjectGraphIterator, also with a null transformer.
     * A null transformer means each element is returned as-is without transformation.
     */
    @Test(timeout = 4000)
    public void test8() throws Throwable {
        Integer rootValue = new Integer(0);

        // Build an iterator over a single Integer root; null transformer means no-op transform
        ObjectGraphIterator<Integer> singleValueIterator =
                new ObjectGraphIterator<Integer>(rootValue, (Transformer<? super Integer, ? extends Integer>) null);

        // Since ObjectGraphIterator implements Iterator, it can itself be used as the root
        // of another ObjectGraphIterator, enabling nested iteration of iterators
        ObjectGraphIterator<Object> nestedIterator =
                new ObjectGraphIterator<Object>(singleValueIterator, (Transformer<? super Object, ?>) null);
    }
}
