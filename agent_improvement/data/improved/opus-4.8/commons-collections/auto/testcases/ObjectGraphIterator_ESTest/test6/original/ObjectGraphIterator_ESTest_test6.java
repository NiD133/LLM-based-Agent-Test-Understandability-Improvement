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
public class ObjectGraphIterator_ESTest_test6 extends ObjectGraphIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test6() throws Throwable {
        Integer integer0 = new Integer(414);
        Transformer<Object, Integer> transformer0 = ConstantTransformer.constantTransformer(integer0);
        ObjectGraphIterator<Integer> objectGraphIterator0 = new ObjectGraphIterator<Integer>(integer0, transformer0);
        ObjectGraphIterator<Integer> objectGraphIterator1 = new ObjectGraphIterator<Integer>(objectGraphIterator0);
        ObjectGraphIterator<Integer> objectGraphIterator2 = new ObjectGraphIterator<Integer>((Integer) null, transformer0);
        objectGraphIterator1.findNextByIterator(objectGraphIterator2);
        assertNotSame(objectGraphIterator2, objectGraphIterator0);
    }
}
