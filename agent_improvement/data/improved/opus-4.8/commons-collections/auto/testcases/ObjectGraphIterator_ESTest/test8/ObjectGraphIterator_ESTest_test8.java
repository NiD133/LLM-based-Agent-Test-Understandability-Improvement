package org.apache.commons.collections4.iterators;

import org.junit.Test;
import org.apache.commons.collections4.Transformer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ObjectGraphIterator_ESTest_test8 extends ObjectGraphIterator_ESTest_scaffolding {

    /**
     * An ObjectGraphIterator can itself be used as the root object of another
     * ObjectGraphIterator. This verifies that both constructions succeed when
     * the transformer is null (which the iterator treats as a no-op transformer).
     */
    @Test(timeout = 4000)
    public void test8() throws Throwable {
        Integer root = new Integer(0);

        // Wrap a plain Integer root with a null (no-op) transformer.
        ObjectGraphIterator<Integer> innerIterator =
                new ObjectGraphIterator<Integer>(root, (Transformer<? super Integer, ? extends Integer>) null);

        // Use the inner iterator itself as the root of an outer iterator.
        ObjectGraphIterator<Object> outerIterator =
                new ObjectGraphIterator<Object>(innerIterator, (Transformer<? super Object, ?>) null);
    }
}
