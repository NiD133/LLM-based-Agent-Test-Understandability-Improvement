package org.apache.commons.collections4.collection;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Collection;
import java.util.LinkedList;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IndexedCollection_ESTest_test08 extends IndexedCollection_ESTest_scaffolding {

    /**
     * Verifies that containsAll returns true when the queried collection
     * is the same as the backing collection of the IndexedCollection.
     * Uses a null-key transformer so all elements are indexed under the null key.
     */
    @Test(timeout = 4000)
    public void test08_containsAll_returnsTrueForBackingCollection() throws Throwable {
        // A transformer that maps every element to null (used as the index key)
        Transformer<Object, Integer> nullKeyTransformer = ConstantTransformer.nullTransformer();

        // Build a source list with one element: the transformer object itself
        LinkedList<Object> sourceList = new LinkedList<Object>();
        sourceList.add((Object) nullKeyTransformer);

        // Create a non-unique IndexedCollection backed by the source list
        IndexedCollection<Integer, Object> indexedCollection =
                IndexedCollection.nonUniqueIndexedCollection((Collection<Object>) sourceList, nullKeyTransformer);

        // Every element from the backing list must be found in the indexed collection
        boolean allElementsContained = indexedCollection.containsAll(sourceList);
        assertTrue(allElementsContained);
    }
}
