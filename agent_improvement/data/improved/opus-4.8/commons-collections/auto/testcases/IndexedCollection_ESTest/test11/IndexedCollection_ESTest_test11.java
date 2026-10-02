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
public class IndexedCollection_ESTest_test11 extends IndexedCollection_ESTest_scaffolding {

    /**
     * Looking up a key in the index of an empty collection returns null,
     * because no value has ever been added to the index.
     */
    @Test(timeout = 4000)
    public void valuesReturnsNullForKeyNotInEmptyIndex() throws Throwable {
        // An empty collection means the index built over it is also empty.
        LinkedList<Object> emptyElements = new LinkedList<Object>();
        Transformer<Object, Object> keyTransformer = ConstantTransformer.nullTransformer();
        IndexedCollection<Object, Object> indexedCollection =
                IndexedCollection.nonUniqueIndexedCollection((Collection<Object>) emptyElements, keyTransformer);

        // The transformer doubles as an arbitrary key here; the index has no entry for it.
        Collection<Object> valuesForMissingKey = indexedCollection.values(keyTransformer);

        assertNull(valuesForMissingKey);
    }
}
