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
public class IndexedCollection_ESTest_test01 extends IndexedCollection_ESTest_scaffolding {

    /**
     * Verifies that retainAll on an empty IndexedCollection with an empty argument
     * returns false, because no elements are removed and the collection is unchanged.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        LinkedList<Object> emptyList = new LinkedList<Object>();
        // nullTransformer always maps every element to null as the index key
        Transformer<Object, Integer> nullKeyTransformer = ConstantTransformer.nullTransformer();
        IndexedCollection<Integer, Object> indexedCollection =
            IndexedCollection.nonUniqueIndexedCollection((Collection<Object>) emptyList, nullKeyTransformer);

        // Retaining all elements of an already-empty collection against an empty keep-set makes no change
        boolean collectionChanged = indexedCollection.retainAll(emptyList);
        assertFalse(collectionChanged);
    }
}
