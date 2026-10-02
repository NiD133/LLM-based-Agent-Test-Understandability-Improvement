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
public class ObjectGraphIterator_ESTest_test6 extends ObjectGraphIterator_ESTest_scaffolding {

    /**
     * Verifies that calling findNextByIterator on one ObjectGraphIterator with a
     * separate ObjectGraphIterator as the argument completes without error, and
     * that the argument iterator remains a distinct instance from the unrelated
     * iterator that was wrapped earlier.
     */
    @Test(timeout = 4000)
    public void findNextByIteratorWithForeignIteratorKeepsInstancesDistinct() throws Throwable {
        // A transformer that always returns the same Integer, regardless of input.
        Integer rootValue = new Integer(414);
        Transformer<Object, Integer> constantTransformer =
                ConstantTransformer.constantTransformer(rootValue);

        // An iterator rooted at a plain value (not itself an iterator).
        ObjectGraphIterator<Integer> valueRootedIterator =
                new ObjectGraphIterator<Integer>(rootValue, constantTransformer);

        // An iterator that wraps the value-rooted iterator as its root iterator.
        ObjectGraphIterator<Integer> wrappingIterator =
                new ObjectGraphIterator<Integer>(valueRootedIterator);

        // A separate iterator rooted at null, passed in as the "next" iterator.
        ObjectGraphIterator<Integer> nullRootedIterator =
                new ObjectGraphIterator<Integer>((Integer) null, constantTransformer);

        wrappingIterator.findNextByIterator(nullRootedIterator);

        // The argument iterator and the originally-wrapped iterator are different objects.
        assertNotSame(nullRootedIterator, valueRootedIterator);
    }
}
