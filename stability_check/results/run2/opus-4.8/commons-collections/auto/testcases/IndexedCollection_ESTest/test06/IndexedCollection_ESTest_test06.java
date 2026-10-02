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
     * when the requested key was never indexed.
     *
     * The collection is built empty, so its index contains no entries at all.
     * Looking up any key must therefore yield {@code null}.
     */
    @Test(timeout = 4000)
    public void get_returnsNull_whenKeyIsNotIndexed() throws Throwable {
        // Decorate an empty collection; nothing gets indexed.
        LinkedList<LinkedList<Object>> emptyElements = new LinkedList<LinkedList<Object>>();
        Transformer<LinkedList<Object>, Object> keyTransformer =
                new ConstantTransformer<LinkedList<Object>, Object>(emptyElements);

        IndexedCollection<Object, LinkedList<Object>> indexedCollection =
                IndexedCollection.nonUniqueIndexedCollection(
                        (Collection<LinkedList<Object>>) emptyElements, keyTransformer);

        // The index is empty, so any lookup returns null.
        LinkedList<Object> lookupResult = indexedCollection.get(keyTransformer);
        assertNull(lookupResult);
    }
}
