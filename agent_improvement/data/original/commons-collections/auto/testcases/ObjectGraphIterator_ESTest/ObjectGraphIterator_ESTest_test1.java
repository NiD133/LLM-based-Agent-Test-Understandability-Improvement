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

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        Integer integer0 = new Integer(2928);
        ObjectGraphIterator<Object> objectGraphIterator0 = new ObjectGraphIterator<Object>(integer0, (Transformer<? super Object, ?>) null);
        ObjectGraphIterator<Integer> objectGraphIterator1 = new ObjectGraphIterator<Integer>(integer0, (Transformer<? super Integer, ? extends Integer>) null);
        objectGraphIterator0.findNextByIterator(objectGraphIterator1);
    }
}
