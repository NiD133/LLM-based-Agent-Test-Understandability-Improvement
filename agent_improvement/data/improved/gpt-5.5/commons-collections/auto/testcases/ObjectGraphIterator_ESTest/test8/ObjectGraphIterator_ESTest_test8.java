package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.collections4.Transformer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ObjectGraphIterator_ESTest_test8 extends ObjectGraphIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test8() throws Throwable {
        Integer rootValue = new Integer(0);

        ObjectGraphIterator<Integer> integerIterator = new ObjectGraphIterator<Integer>(
                rootValue,
                (Transformer<? super Integer, ? extends Integer>) null);

        ObjectGraphIterator<Object> nestedIterator = new ObjectGraphIterator<Object>(
                integerIterator,
                (Transformer<? super Object, ?>) null);
    }
}
