package org.apache.commons.collections4.collection;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Collection;
import java.util.LinkedList;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.functors.CloneTransformer;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IndexedCollection_ESTest_test10 extends IndexedCollection_ESTest_scaffolding {

    /**
     * Verifies that addAll transfers elements from one IndexedCollection into another,
     * even when the source collection's backing list was modified directly (bypassing the index).
     * The target's backing list should reflect the added elements and addAll should return true.
     */
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        // Source: a unique IndexedCollection backed by a LinkedList with a constant key transformer.
        // The element is added directly to the backing list, bypassing the index,
        // so the element is present when the collection is iterated by addAll.
        LinkedList<Integer> sourceBackingList = new LinkedList<Integer>();
        Integer zero = new Integer(0);
        ConstantTransformer<Integer, Integer> constantKeyTransformer =
                new ConstantTransformer<Integer, Integer>(zero);
        IndexedCollection<Integer, Integer> sourceCollection = IndexedCollection.uniqueIndexedCollection(
                (Collection<Integer>) sourceBackingList,
                (Transformer<Integer, Integer>) constantKeyTransformer);
        sourceBackingList.add(zero); // direct modification bypasses the index

        // Target: a unique IndexedCollection backed by an empty LinkedList with a clone key transformer.
        Transformer<Object, Object> cloneKeyTransformer = CloneTransformer.cloneTransformer();
        LinkedList<Object> targetBackingList = new LinkedList<Object>();
        IndexedCollection<Object, Object> targetCollection = IndexedCollection.uniqueIndexedCollection(
                (Collection<Object>) targetBackingList, cloneKeyTransformer);

        // Add all elements from the source into the target.
        boolean collectionWasModified = targetCollection.addAll(sourceCollection);

        // The element (Integer 0) should now be in the target's backing list,
        // and addAll should report that the collection was modified.
        assertTrue(targetBackingList.contains(0));
        assertTrue(collectionWasModified);
    }
}
