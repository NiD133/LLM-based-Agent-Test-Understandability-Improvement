package org.apache.commons.collections4.collection;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Collection;
import java.util.LinkedList;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IndexedCollection_ESTest_test00 extends IndexedCollection_ESTest_scaffolding {

    /**
     * Verifies that retainAll on a non-empty IndexedCollection returns true and
     * removes all elements when the retain list is empty.
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // Build a unique IndexedCollection backed by an empty list;
        // the ConstantTransformer maps every element to the same key (value 0).
        LinkedList<Integer> backingList = new LinkedList<Integer>();
        Integer elementValue = new Integer(0);
        ConstantTransformer<Integer, Integer> constantKeyTransformer =
                new ConstantTransformer<Integer, Integer>(elementValue);
        IndexedCollection<Integer, Integer> indexedCollection =
                IndexedCollection.uniqueIndexedCollection(
                        (Collection<Integer>) backingList,
                        (Transformer<Integer, Integer>) constantKeyTransformer);

        // Add one element so the collection is non-empty
        indexedCollection.add(elementValue);

        // retainAll with an empty collection must remove all elements and signal a change
        LinkedList<Object> emptyRetainList = new LinkedList<Object>();
        boolean collectionWasModified = indexedCollection.retainAll(emptyRetainList);
        assertTrue(collectionWasModified);
    }
}
