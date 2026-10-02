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
public class IndexedCollection_ESTest_test13 extends IndexedCollection_ESTest_scaffolding {

    /**
     * Verifies that removeAll delegates removal to the underlying collection,
     * so elements are removed from the backing list as well as the index.
     *
     * The null-key transformer maps every element to the same null key,
     * meaning the indexed collection treats all elements as equivalent under the index.
     *
     * An element is added directly to the backing list (bypassing the indexed collection),
     * then removeAll is called with that same list. Because removeAll iterates over
     * the argument collection and calls remove() on the indexed collection (which in turn
     * removes from the backing list), the element ends up removed from the backing list itself,
     * leaving it empty.
     */
    @Test(timeout = 4000)
    public void test_removeAll_removesElementFromBackingListAndReturnsTrue() throws Throwable {
        // Set up an empty backing list and a transformer that always produces a null key
        LinkedList<Object> backingList = new LinkedList<Object>();
        Transformer<Object, Integer> nullKeyTransformer = ConstantTransformer.nullTransformer();

        // Wrap the backing list in a non-unique indexed collection
        IndexedCollection<Integer, Object> indexedCollection = IndexedCollection.nonUniqueIndexedCollection(
                (Collection<Object>) backingList, nullKeyTransformer);

        // Add an element directly to the backing list, bypassing the indexed collection
        backingList.add((Object) nullKeyTransformer);

        // Remove all elements in backingList from the indexed collection;
        // since the indexed collection's remove() delegates to the backing list,
        // this also empties backingList
        boolean changed = indexedCollection.removeAll(backingList);

        // The backing list should now be empty because the element was removed through the index
        assertEquals(0, backingList.size());
        // removeAll should report that the collection changed
        assertTrue(changed);
    }
}
