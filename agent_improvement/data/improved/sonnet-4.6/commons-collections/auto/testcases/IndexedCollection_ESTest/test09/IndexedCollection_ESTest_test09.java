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
     * Verifies that adding an element to a uniquely-indexed collection throws
     * IllegalArgumentException when the key transformer maps the new element
     * to a key that is already present in the index.
     *
     * Setup: a null-key transformer always produces null as the index key,
     * so any two distinct elements will collide on the same key (null).
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        // A transformer that maps every object to null, so all elements share the same index key.
        Transformer<Object, Object> nullKeyTransformer = ConstantTransformer.nullTransformer();

        // Seed the backing collection with one element so the index already contains key=null.
        LinkedList<Object> backingList = new LinkedList<Object>();
        backingList.offerFirst(nullKeyTransformer);

        // Build a unique indexed collection; reindex() runs during construction and
        // registers nullKeyTransformer under key=null.
        IndexedCollection<Object, Object> uniqueCollection =
                IndexedCollection.uniqueIndexedCollection((Collection<Object>) backingList, nullKeyTransformer);

        // Attempting to add another element also produces key=null, which duplicates the
        // existing entry and must be rejected with IllegalArgumentException.
        try {
            uniqueCollection.add(backingList);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Duplicate key in uniquely indexed collection.
            //
            verifyException("org.apache.commons.collections4.collection.IndexedCollection", e);
        }
    }
}
