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
     * Verifies that removeAll deletes every element shared with the given
     * collection and reports that the collection changed.
     *
     * The IndexedCollection decorates {@code backingList}, so after a single
     * element is added to that list, calling removeAll with the same list
     * removes that element and leaves the backing list empty.
     */
    @Test(timeout = 4000)
    public void test13() throws Throwable {
        LinkedList<Object> backingList = new LinkedList<Object>();
        Transformer<Object, Integer> keyTransformer = ConstantTransformer.nullTransformer();
        IndexedCollection<Integer, Object> indexedCollection =
                IndexedCollection.nonUniqueIndexedCollection((Collection<Object>) backingList, keyTransformer);

        // Add a single element to the decorated collection.
        backingList.add((Object) keyTransformer);

        // Removing all elements of backingList empties it and signals a change.
        boolean changed = indexedCollection.removeAll(backingList);

        assertEquals(0, backingList.size());
        assertTrue(changed);
    }
}
