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
        LinkedList<Object> linkedList0 = new LinkedList<Object>();
        Object object0 = new Object();
        IdentityPredicate<Object> identityPredicate0 = new IdentityPredicate<Object>(object0);
        PredicateTransformer<Transformer<Boolean, Boolean>> predicateTransformer0 = new PredicateTransformer<Transformer<Boolean, Boolean>>(identityPredicate0);
        Boolean boolean0 = predicateTransformer0.transform((Transformer<Boolean, Boolean>) null);
        linkedList0.add((Object) boolean0);
        ListIterator<Object> listIterator0 = linkedList0.listIterator();
        ObjectGraphIterator<Object> objectGraphIterator0 = new ObjectGraphIterator<Object>(listIterator0);
        objectGraphIterator0.next();
        objectGraphIterator0.remove();
    }
}
