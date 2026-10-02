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
     * Verifies that an ObjectGraphIterator built from a plain ListIterator can
     * advance to its single element via next() and then delegate remove() to
     * the underlying iterator without error.
     */
    @Test(timeout = 4000)
    public void next_thenRemove_onSingleElementList() throws Throwable {
        // Build a single element to store in the list. An IdentityPredicate that
        // checks reference equality against a fresh Object, wrapped in a
        // PredicateTransformer, yields Boolean.FALSE when transforming null
        // (null is not the same reference as the object).
        Object referenceObject = new Object();
        IdentityPredicate<Object> identityPredicate = new IdentityPredicate<Object>(referenceObject);
        PredicateTransformer<Transformer<Boolean, Boolean>> predicateTransformer =
                new PredicateTransformer<Transformer<Boolean, Boolean>>(identityPredicate);
        Boolean elementValue = predicateTransformer.transform((Transformer<Boolean, Boolean>) null);

        // A list holding that single element, exposed through its ListIterator.
        LinkedList<Object> elements = new LinkedList<Object>();
        elements.add((Object) elementValue);
        ListIterator<Object> listIterator = elements.listIterator();

        // The ObjectGraphIterator simply walks the supplied root iterator.
        ObjectGraphIterator<Object> graphIterator = new ObjectGraphIterator<Object>(listIterator);

        // Consume the only element, then remove it through the underlying iterator.
        graphIterator.next();
        graphIterator.remove();
    }
}
