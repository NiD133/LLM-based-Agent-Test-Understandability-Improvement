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
public class IndexedCollection_ESTest_test04 extends IndexedCollection_ESTest_scaffolding {

    /**
     * Verifies that {@link IndexedCollection#removeIf(java.util.function.Predicate)}
     * returns {@code false} (no change) when given a {@code null} filter, as the
     * method short-circuits on a null predicate.
     */
    @Test(timeout = 4000)
    public void removeIfWithNullFilterReturnsFalse() throws Throwable {
        LinkedList<Object> backingList = new LinkedList<Object>();
        Transformer<Object, Integer> keyTransformer = ConstantTransformer.nullTransformer();
        IndexedCollection<Integer, Object> indexedCollection =
                IndexedCollection.nonUniqueIndexedCollection((Collection<Object>) backingList, keyTransformer);

        boolean collectionChanged = indexedCollection.removeIf((java.util.function.Predicate<? super Object>) null);

        assertFalse(collectionChanged);
    }
}
