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
public class IndexedCollection_ESTest_test05 extends IndexedCollection_ESTest_scaffolding {

    /**
     * Removing an element from an IndexedCollection that wraps an empty
     * collection should report no change (return {@code false}), because
     * there is nothing to remove.
     */
    @Test(timeout = 4000)
    public void removeFromEmptyCollectionReturnsFalse() throws Throwable {
        LinkedList<Object> emptyBackingList = new LinkedList<Object>();
        Transformer<Object, Integer> keyTransformer = ConstantTransformer.nullTransformer();
        IndexedCollection<Integer, Object> indexedCollection =
                IndexedCollection.nonUniqueIndexedCollection((Collection<Object>) emptyBackingList, keyTransformer);

        boolean removed = indexedCollection.remove(keyTransformer);

        assertFalse(removed);
    }
}
