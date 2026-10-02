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
public class IndexedCollection_ESTest_test09 extends IndexedCollection_ESTest_scaffolding {

    /**
     * A unique IndexedCollection must reject a second element whose computed
     * index key collides with one already present.
     *
     * Here the key transformer always returns null, so every element maps to the
     * same (null) key. The backing list already holds one element when the unique
     * collection is built, so adding a second element triggers a duplicate-key
     * IllegalArgumentException.
     */
    @Test(timeout = 4000)
    public void addingDuplicateKeyToUniqueIndexThrows() throws Throwable {
        // A transformer that maps every value to the same key (null).
        Transformer<Object, Object> nullKeyTransformer = ConstantTransformer.nullTransformer();

        // Seed the backing collection with one element before indexing.
        LinkedList<Object> backingList = new LinkedList<Object>();
        backingList.offerFirst(nullKeyTransformer);

        IndexedCollection<Object, Object> uniqueIndex =
                IndexedCollection.uniqueIndexedCollection((Collection<Object>) backingList, nullKeyTransformer);

        // The existing element already occupies the null key, so any further
        // addition collides and must be rejected.
        try {
            uniqueIndex.add(backingList);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Duplicate key in uniquely indexed collection.
            verifyException("org.apache.commons.collections4.collection.IndexedCollection", e);
        }
    }
}
