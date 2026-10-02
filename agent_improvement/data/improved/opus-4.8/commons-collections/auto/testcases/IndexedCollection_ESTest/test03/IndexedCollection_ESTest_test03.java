package org.apache.commons.collections4.collection;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Collection;
import java.util.LinkedList;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.apache.commons.collections4.functors.TruePredicate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IndexedCollection_ESTest_test03 extends IndexedCollection_ESTest_scaffolding {

    /**
     * Verifies that {@code removeIf} with an always-true predicate removes every
     * element from the decorated collection and reports that a change occurred.
     */
    @Test(timeout = 4000)
    public void removeIf_withAlwaysTruePredicate_clearsCollectionAndReturnsTrue() throws Throwable {
        // A key transformer that always maps an element to null; suitable for a non-unique index.
        Transformer<Object, Integer> keyTransformer = ConstantTransformer.nullTransformer();

        // Back the indexed collection with a list holding a single element.
        LinkedList<Object> backingList = new LinkedList<Object>();
        backingList.add(keyTransformer);
        IndexedCollection<Integer, Object> indexedCollection =
                IndexedCollection.nonUniqueIndexedCollection((Collection<Object>) backingList, keyTransformer);

        // A predicate that matches every element, so removeIf should remove all of them.
        Predicate<Object> matchEverything = TruePredicate.truePredicate();
        boolean anyRemoved = indexedCollection.removeIf(matchEverything);

        assertEquals(0, backingList.size());
        assertTrue(anyRemoved);
    }
}
