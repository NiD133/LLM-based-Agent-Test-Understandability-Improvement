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
public class IndexedCollection_ESTest_test06 extends IndexedCollection_ESTest_scaffolding {

    /**
     * Verifies that get() returns null when the looked-up key is absent from the index.
     * The backing collection is empty, so no elements are indexed and any key lookup returns null.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // Empty list used as the backing collection; no elements will be indexed
        LinkedList<LinkedList<Object>> emptyBackingCollection = new LinkedList<LinkedList<Object>>();

        // Transformer that always returns the same constant object as the index key
        ConstantTransformer<LinkedList<Object>, Object> constantKeyTransformer =
                new ConstantTransformer<LinkedList<Object>, Object>(emptyBackingCollection);

        // Build a non-unique indexed collection over the empty backing collection
        IndexedCollection<Object, LinkedList<Object>> indexedCollection =
                IndexedCollection.nonUniqueIndexedCollection(
                        (Collection<LinkedList<Object>>) emptyBackingCollection,
                        (Transformer<LinkedList<Object>, Object>) constantKeyTransformer);

        // Looking up a key that was never indexed should return null
        LinkedList<Object> result = indexedCollection.get(constantKeyTransformer);
        assertNull(result);
    }
}
