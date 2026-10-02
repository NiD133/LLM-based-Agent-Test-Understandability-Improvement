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
public class ObjectGraphIterator_ESTest_test4 extends ObjectGraphIterator_ESTest_scaffolding {

    /**
     * Verifies that findNextByIterator accepts a separate ObjectGraphIterator instance
     * and that the two iterator objects remain distinct after the call.
     *
     * A ConstantTransformer is used so that any element encountered during traversal
     * is always mapped to the same constant value (419), making the graph finite and
     * deterministic. Two independent iterators are created from the same root and
     * transformer; one is fed into the other via findNextByIterator, and afterward
     * the two references must still point to different objects.
     */
    @Test(timeout = 4000)
    public void test4() throws Throwable {
        // A constant value that both iterators use as their root element
        Integer constantValue = new Integer(419);

        // ConstantTransformer always returns constantValue regardless of the input element
        Transformer<Object, Integer> constantTransformer = ConstantTransformer.constantTransformer(constantValue);

        // Two independent iterators sharing the same root and transformer
        ObjectGraphIterator<Integer> primaryIterator   = new ObjectGraphIterator<Integer>(constantValue, constantTransformer);
        ObjectGraphIterator<Integer> secondaryIterator = new ObjectGraphIterator<Integer>(constantValue, constantTransformer);

        // Drive primaryIterator's traversal using secondaryIterator as the source;
        // this exercises the branch in findNextByIterator where the supplied iterator
        // differs from the current one and must be pushed onto the internal stack.
        primaryIterator.findNextByIterator(secondaryIterator);

        // The two iterator instances must remain distinct objects after the call
        assertNotSame(secondaryIterator, primaryIterator);
    }
}
