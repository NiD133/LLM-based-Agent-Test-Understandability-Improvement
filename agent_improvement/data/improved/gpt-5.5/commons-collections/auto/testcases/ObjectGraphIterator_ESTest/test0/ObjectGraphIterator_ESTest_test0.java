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
public class ObjectGraphIterator_ESTest_test0 extends ObjectGraphIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        LinkedList<Object> rootValues = new LinkedList<Object>();

        Object predicateTarget = new Object();
        IdentityPredicate<Object> matchesPredicateTarget = new IdentityPredicate<Object>(predicateTarget);
        PredicateTransformer<Transformer<Boolean, Boolean>> predicateAsTransformer =
                new PredicateTransformer<Transformer<Boolean, Boolean>>(matchesPredicateTarget);

        Boolean transformedNullInput = predicateAsTransformer.transform((Transformer<Boolean, Boolean>) null);
        rootValues.add((Object) transformedNullInput);

        ListIterator<Object> rootIterator = rootValues.listIterator();
        ObjectGraphIterator<Object> graphIterator = new ObjectGraphIterator<Object>(rootIterator);

        graphIterator.next();
        graphIterator.remove();
    }
}
