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
     * Verifies that {@link IndexedCollection#get(Object)} returns null when the
     * requested key has never been indexed (the backing collection is empty).
     */
    @Test(timeout = 4000)
    public void testGetReturnsNullForAbsentKey() throws Throwable {
        // Empty backing collection — no elements will be added to the index
        LinkedList<LinkedList<Object>> backingCollection = new LinkedList<LinkedList<Object>>();

        // ConstantTransformer always produces the same key regardless of input element
        ConstantTransformer<LinkedList<Object>, Object> keyTransformer =
            new ConstantTransformer<LinkedList<Object>, Object>(backingCollection);

        // Create a non-unique indexed collection over the empty backing collection
        IndexedCollection<Object, LinkedList<Object>> indexedCollection =
            IndexedCollection.nonUniqueIndexedCollection(
                (Collection<LinkedList<Object>>) backingCollection,
                (Transformer<LinkedList<Object>, Object>) keyTransformer);

        // The keyTransformer object is used as a lookup key; since nothing was indexed
        // under that key, get() must return null
        LinkedList<Object> result = indexedCollection.get(keyTransformer);
        assertNull(result);
    }
}
