package org.apache.commons.collections4.collection;

import org.junit.Test;
import java.util.Collection;
import java.util.LinkedList;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IndexedCollection_ESTest_test12 extends IndexedCollection_ESTest_scaffolding {

    /**
     * Verifies that clearing a non-unique IndexedCollection backed by an empty
     * collection completes without error.
     */
    @Test(timeout = 4000)
    public void clearEmptyNonUniqueIndexedCollection() throws Throwable {
        // Empty collection to be decorated and indexed.
        LinkedList<LinkedList<Object>> backingCollection = new LinkedList<LinkedList<Object>>();

        // Key transformer that always maps every element to the same constant key.
        ConstantTransformer<LinkedList<Object>, Object> keyTransformer =
                new ConstantTransformer<LinkedList<Object>, Object>(backingCollection);

        IndexedCollection<Object, LinkedList<Object>> indexedCollection =
                IndexedCollection.nonUniqueIndexedCollection(
                        (Collection<LinkedList<Object>>) backingCollection,
                        (Transformer<LinkedList<Object>, Object>) keyTransformer);

        // Clearing an already-empty indexed collection should succeed silently.
        indexedCollection.clear();
    }
}
