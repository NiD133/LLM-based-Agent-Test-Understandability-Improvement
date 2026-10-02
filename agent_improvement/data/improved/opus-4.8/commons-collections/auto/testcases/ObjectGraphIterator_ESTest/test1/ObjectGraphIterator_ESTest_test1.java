package org.apache.commons.collections4.iterators;

import org.junit.Test;
import org.apache.commons.collections4.Transformer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ObjectGraphIterator_ESTest_test1 extends ObjectGraphIterator_ESTest_scaffolding {

    /**
     * Verifies that {@link ObjectGraphIterator#findNextByIterator(java.util.Iterator)}
     * can advance one ObjectGraphIterator by drawing elements from another one used
     * as its backing iterator. Both iterators are built with a null transformer, so
     * the single root value (the Integer) is passed through unchanged.
     */
    @Test(timeout = 4000)
    public void findNextByIteratorConsumesElementsFromAnotherIterator() throws Throwable {
        Integer rootValue = new Integer(2928);

        // Outer iterator: receives its elements from the inner iterator below.
        ObjectGraphIterator<Object> outerIterator =
                new ObjectGraphIterator<Object>(rootValue, (Transformer<? super Object, ?>) null);

        // Inner iterator: yields the single root value as its only element.
        ObjectGraphIterator<Integer> innerIterator =
                new ObjectGraphIterator<Integer>(rootValue, (Transformer<? super Integer, ? extends Integer>) null);

        // Drive the outer iterator forward using the inner iterator as its source.
        outerIterator.findNextByIterator(innerIterator);
    }
}
