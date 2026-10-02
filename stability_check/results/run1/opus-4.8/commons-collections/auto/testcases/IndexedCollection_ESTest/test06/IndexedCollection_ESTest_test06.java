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
     * Verifies that {@link IndexedCollection#get(Object)} returns {@code null}
     * when the index does not contain the requested key.
     *
     * <p>The collection is built empty, so its index holds no entries. Looking
     * up any key (here the transformer instance itself) must therefore yield
     * {@code null}.</p>
     */
    @Test(timeout = 4000)
    public void get_returnsNull_whenKeyNotIndexed() throws Throwable {
        // An empty backing collection means the index starts out empty.
        LinkedList<LinkedList<Object>> emptyBackingCollection = new LinkedList<LinkedList<Object>>();

        // Key transformer that always maps any element to the same constant key.
        ConstantTransformer<LinkedList<Object>, Object> keyTransformer =
                new ConstantTransformer<LinkedList<Object>, Object>(emptyBackingCollection);

        IndexedCollection<Object, LinkedList<Object>> indexedCollection =
                IndexedCollection.nonUniqueIndexedCollection(
                        (Collection<LinkedList<Object>>) emptyBackingCollection,
                        (Transformer<LinkedList<Object>, Object>) keyTransformer);

        // No element was ever indexed, so any key lookup returns null.
        LinkedList<Object> lookupResult = indexedCollection.get(keyTransformer);

        assertNull(lookupResult);
    }
}
