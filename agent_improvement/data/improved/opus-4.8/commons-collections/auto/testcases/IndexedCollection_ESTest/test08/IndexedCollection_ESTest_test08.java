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
public class IndexedCollection_ESTest_test08 extends IndexedCollection_ESTest_scaffolding {

    /**
     * Verifies that an IndexedCollection reports {@code containsAll} as true when
     * checked against the exact same elements it was built to decorate.
     */
    @Test(timeout = 4000)
    public void containsAllReturnsTrueForOwnElements() throws Throwable {
        // A key transformer that always maps every element to the null key.
        Transformer<Object, Integer> keyTransformer = ConstantTransformer.nullTransformer();

        // Backing collection holds a single element (the transformer instance itself).
        LinkedList<Object> backingList = new LinkedList<Object>();
        backingList.add((Object) keyTransformer);

        IndexedCollection<Integer, Object> indexedCollection =
                IndexedCollection.nonUniqueIndexedCollection((Collection<Object>) backingList, keyTransformer);

        // The collection was indexed from backingList, so it must contain all of its elements.
        boolean containsAllOwnElements = indexedCollection.containsAll(backingList);

        assertTrue(containsAllOwnElements);
    }
}
