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
public class ObjectGraphIterator_ESTest_test5 extends ObjectGraphIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test5() throws Throwable {
        Integer rootValue = new Integer(419);
        Transformer<Object, Integer> constantRootTransformer = ConstantTransformer.constantTransformer(rootValue);
        ObjectGraphIterator<Integer> innerIterator =
                new ObjectGraphIterator<Integer>(rootValue, constantRootTransformer);
        ObjectGraphIterator<Integer> iteratorRootedAtInnerIterator =
                new ObjectGraphIterator<Integer>(innerIterator);

        innerIterator.findNext(rootValue);
        Integer nextValue = iteratorRootedAtInnerIterator.next();

        assertEquals(419, (int) nextValue);
    }
}
