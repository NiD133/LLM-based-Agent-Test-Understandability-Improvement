package org.apache.commons.collections4.collection;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Collection;
import java.util.LinkedList;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.functors.CloneTransformer;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.apache.commons.collections4.functors.NullPredicate;
import org.apache.commons.collections4.functors.TruePredicate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IndexedCollection_ESTest_test07 extends IndexedCollection_ESTest_scaffolding {

    /**
     * Verifies that containsAll returns false when an element is added directly
     * to the backing collection, bypassing the decorator's add method.
     *
     * Because IndexedCollection does not observe mutations made directly to
     * the underlying collection, its index stays empty even after
     * backingList.add(...). The subsequent containsAll call checks the stale
     * index and therefore returns false.
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        // Set up an empty backing list and a key transformer that always produces null
        LinkedList<Object> backingList = new LinkedList<Object>();
        Transformer<Object, Integer> nullKeyTransformer = ConstantTransformer.nullTransformer();

        // Wrap the backing list in a non-unique IndexedCollection (index is built at construction time, so it is empty)
        IndexedCollection<Integer, Object> indexedCollection =
                IndexedCollection.nonUniqueIndexedCollection((Collection<Object>) backingList, nullKeyTransformer);

        // Add the indexedCollection directly to the backing list, bypassing the decorator —
        // the internal index is NOT updated by this operation
        backingList.add((Object) indexedCollection);

        // containsAll checks the index, which is still empty, so it returns false
        boolean containsAllElements = indexedCollection.containsAll(backingList);
        assertFalse(containsAllElements);
    }
}
