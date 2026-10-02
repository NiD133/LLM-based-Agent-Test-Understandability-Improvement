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
     * Verifies that retainAll reports a modification (returns true) when it
     * removes the collection's only element. Retaining against an empty
     * collection drops every existing element, so the indexed collection
     * changes and retainAll returns true.
     */
    @Test(timeout = 4000)
    public void retainAllAgainstEmptyCollectionRemovesElementAndReturnsTrue() throws Throwable {
        // Build a unique-indexed collection backed by an empty list. Every
        // element is keyed to the constant value 0 by the key transformer.
        Integer zeroKey = new Integer(0);
        Transformer<Integer, Integer> keyTransformer = new ConstantTransformer<Integer, Integer>(zeroKey);
        Collection<Integer> backingList = new LinkedList<Integer>();
        IndexedCollection<Integer, Integer> indexedCollection =
                IndexedCollection.uniqueIndexedCollection(backingList, keyTransformer);

        // Add the single element that the retainAll call will later drop.
        indexedCollection.add(zeroKey);

        // Retaining only the elements present in an empty collection removes
        // everything, so the collection is modified.
        boolean modified = indexedCollection.retainAll(new LinkedList<Object>());

        assertTrue(modified);
    }
}
