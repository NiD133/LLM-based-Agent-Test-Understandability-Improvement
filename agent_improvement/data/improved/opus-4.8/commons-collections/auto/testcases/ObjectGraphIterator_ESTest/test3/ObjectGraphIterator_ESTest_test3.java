package org.apache.commons.collections4.iterators;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ObjectGraphIterator_ESTest_test3 extends ObjectGraphIterator_ESTest_scaffolding {

    /**
     * Calling next() on an ObjectGraphIterator backed by an empty iterator
     * must throw NoSuchElementException, since there are no elements to return.
     */
    @Test(timeout = 4000)
    public void nextOnEmptyIteratorThrowsNoSuchElement() throws Throwable {
        // Wrap an empty list's iterator so the graph iterator has nothing to traverse.
        LinkedList<Object> emptyList = new LinkedList<Object>();
        Iterator<Object> emptyIterator = emptyList.iterator();
        ObjectGraphIterator<Object> graphIterator = new ObjectGraphIterator<Object>(emptyIterator);

        try {
            graphIterator.next();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            // next() with no remaining elements should fail from within ObjectGraphIterator.
            verifyException("org.apache.commons.collections4.iterators.ObjectGraphIterator", e);
        }
    }
}
