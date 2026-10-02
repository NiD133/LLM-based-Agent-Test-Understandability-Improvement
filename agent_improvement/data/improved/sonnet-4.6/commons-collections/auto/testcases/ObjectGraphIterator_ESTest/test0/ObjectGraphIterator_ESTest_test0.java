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

    /**
     * Verifies that ObjectGraphIterator.remove() delegates to the underlying
     * iterator after a successful next() call.
     *
     * A list is populated with a single Boolean value produced by applying a
     * PredicateTransformer (backed by an IdentityPredicate) to null.  Because
     * null is never the same reference as the predicate's target object, the
     * transform returns Boolean.FALSE.  The list iterator is then wrapped in an
     * ObjectGraphIterator, one element is consumed via next(), and remove() is
     * called to confirm delegation to the underlying ListIterator.
     */
    @Test(timeout = 4000)
    public void test0() throws Throwable {
        // --- Setup: build a single-element list containing Boolean.FALSE ---
        LinkedList<Object> list = new LinkedList<Object>();

        // IdentityPredicate returns false when its input is not the same instance
        // as the reference object; passing null will always yield false.
        Object referenceObject = new Object();
        IdentityPredicate<Object> identityPredicate =
            new IdentityPredicate<Object>(referenceObject);
        PredicateTransformer<Transformer<Boolean, Boolean>> predicateTransformer =
            new PredicateTransformer<Transformer<Boolean, Boolean>>(identityPredicate);

        // transform(null) evaluates the predicate on null → Boolean.FALSE
        Boolean predicateResult = predicateTransformer.transform((Transformer<Boolean, Boolean>) null);
        list.add((Object) predicateResult);

        // --- Exercise: wrap the list iterator and call next() then remove() ---
        ListIterator<Object> listIterator = list.listIterator();
        ObjectGraphIterator<Object> graphIterator = new ObjectGraphIterator<Object>(listIterator);
        graphIterator.next();
        graphIterator.remove();
    }
}
