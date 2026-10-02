package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ObjectGraphIterator_ESTest_test2 extends ObjectGraphIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        Integer rootValue = new Integer(414);
        Transformer<Object, Integer> constantValueTransformer = ConstantTransformer.constantTransformer(rootValue);
        ObjectGraphIterator<Integer> iteratorWithTransformedRoot =
                new ObjectGraphIterator<Integer>(rootValue, constantValueTransformer);
        ObjectGraphIterator<Integer> iteratorOverNestedIterator =
                new ObjectGraphIterator<Integer>(iteratorWithTransformedRoot);

        iteratorOverNestedIterator.next();

        try {
            iteratorOverNestedIterator.remove();
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            verifyException("org.apache.commons.collections4.iterators.ObjectGraphIterator", e);
        }
    }
}
