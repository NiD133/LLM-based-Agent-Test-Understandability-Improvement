/*
 * Improved for understandability from EvoSuite-generated test.
 */

package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CartesianProductIterator_ESTest extends CartesianProductIterator_ESTest_scaffolding {

    /**
     * Verifies that consecutive next() calls advance the iterator and return
     * distinct tuples from the Cartesian product.
     *
     * Setup: 3 dimensions — first two each have 4 elements, third has 1 element.
     * The total product has 4×4×1 = 16 combinations.
     */
    @Test(timeout = 4000)
    @SuppressWarnings("unchecked")
    public void consecutiveNextCallsReturnDistinctTuples() throws Throwable {
        List<String> firstDimension  = Arrays.asList("A", "B", "C", "D");
        List<String> secondDimension = Arrays.asList("A", "B", "C", "D");
        List<String> thirdDimension  = Arrays.asList("X");

        Iterable<String>[] dimensions = new Iterable[]{firstDimension, secondDimension, thirdDimension};
        CartesianProductIterator<Object> iterator = new CartesianProductIterator<>(dimensions);

        List<Object> firstTuple = iterator.next();
        assertFalse(firstTuple.isEmpty());

        List<Object> secondTuple = iterator.next();
        assertFalse(secondTuple.equals(firstTuple));
    }

    /**
     * Verifies that next() throws NoSuchElementException when the iterator
     * is constructed from zero dimensions (empty Cartesian product).
     */
    @Test(timeout = 4000)
    @SuppressWarnings("unchecked")
    public void nextThrowsNoSuchElementExceptionWhenProductIsEmpty() throws Throwable {
        Iterable<Object>[] noDimensions = new Iterable[0];
        CartesianProductIterator<Object> iterator = new CartesianProductIterator<>(noDimensions);

        try {
            iterator.next();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            verifyException("org.apache.commons.collections4.iterators.CartesianProductIterator", e);
        }
    }

    /**
     * Verifies that the constructor completes without error when the first
     * iterable is empty. The constructor short-circuits on an empty iterable
     * (the Cartesian product is empty), so the remaining null slots in the
     * array are never accessed.
     */
    @Test(timeout = 4000)
    @SuppressWarnings("unchecked")
    public void constructorShortCircuitsWhenFirstIterableIsEmpty() throws Throwable {
        Iterable<String>[] dimensions = new Iterable[4];
        dimensions[0] = new ArrayList<>(); // empty — triggers early exit; slots [1..3] remain null and are never reached

        new CartesianProductIterator<>(dimensions);
    }

    /**
     * Verifies that remove() always throws UnsupportedOperationException,
     * since CartesianProductIterator does not support element removal.
     */
    @Test(timeout = 4000)
    @SuppressWarnings("unchecked")
    public void removeAlwaysThrowsUnsupportedOperationException() throws Throwable {
        Iterable<Object>[] noDimensions = new Iterable[0];
        CartesianProductIterator<Object> iterator = new CartesianProductIterator<>(noDimensions);

        try {
            iterator.remove();
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            verifyException("org.apache.commons.collections4.iterators.CartesianProductIterator", e);
        }
    }
}
