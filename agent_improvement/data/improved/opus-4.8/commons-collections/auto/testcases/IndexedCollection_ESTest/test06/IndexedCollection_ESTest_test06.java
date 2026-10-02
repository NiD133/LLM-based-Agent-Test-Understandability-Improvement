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
public class IndexedCollection_ESTest_test06 extends IndexedCollection_ESTest_scaffolding {

    /**
     * Verifies that {@link IndexedCollection#get(Object)} returns {@code null}
     * when the index is empty: the backing collection has no elements, so no
     * key maps to any value regardless of the key supplied to the lookup.
     */
    @Test(timeout = 4000)
    public void getOnEmptyIndexedCollectionReturnsNull() throws Throwable {
        // An empty backing collection means the index contains no entries.
        LinkedList<LinkedList<Object>> emptyBackingCollection = new LinkedList<LinkedList<Object>>();

        // The key transformer always maps any element to the same constant key.
        ConstantTransformer<LinkedList<Object>, Object> keyTransformer =
                new ConstantTransformer<LinkedList<Object>, Object>(emptyBackingCollection);

        IndexedCollection<Object, LinkedList<Object>> indexedCollection =
                IndexedCollection.nonUniqueIndexedCollection(
                        (Collection<LinkedList<Object>>) emptyBackingCollection,
                        (Transformer<LinkedList<Object>, Object>) keyTransformer);

        // Looking up any key on an empty index yields no element.
        LinkedList<Object> lookupResult = indexedCollection.get(keyTransformer);

        assertNull(lookupResult);
    }
}
