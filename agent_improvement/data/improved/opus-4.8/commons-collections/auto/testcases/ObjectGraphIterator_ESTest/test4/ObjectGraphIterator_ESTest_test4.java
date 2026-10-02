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
public class ObjectGraphIterator_ESTest_test4 extends ObjectGraphIterator_ESTest_scaffolding {

    /**
     * Verifies that {@code findNextByIterator} can advance an iterator using a
     * second, distinct {@code ObjectGraphIterator} as the source iterator.
     * The two iterators are built from the same root value and transformer but
     * remain separate object instances.
     */
    @Test(timeout = 4000)
    public void findNextByIteratorUsesGivenIteratorAsSource() throws Throwable {
        Integer rootValue = Integer.valueOf(419);
        Transformer<Object, Integer> constantTransformer =
                ConstantTransformer.constantTransformer(rootValue);

        ObjectGraphIterator<Integer> graphIterator =
                new ObjectGraphIterator<Integer>(rootValue, constantTransformer);
        ObjectGraphIterator<Integer> sourceIterator =
                new ObjectGraphIterator<Integer>(rootValue, constantTransformer);

        graphIterator.findNextByIterator(sourceIterator);

        assertNotSame(sourceIterator, graphIterator);
    }
}
