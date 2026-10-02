package org.apache.commons.collections4.collection;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Collection;
import java.util.LinkedList;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.apache.commons.collections4.functors.NullPredicate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IndexedCollection_ESTest_test02 extends IndexedCollection_ESTest_scaffolding {

    /**
     * removeIf should remove nothing when no element matches the predicate.
     *
     * The backing collection holds a single, non-null element, while the
     * predicate only matches null elements. Therefore removeIf must report
     * that the collection was not modified and the element count stays at 1.
     */
    @Test(timeout = 4000)
    public void removeIf_withNonMatchingPredicate_removesNothing() throws Throwable {
        // Backing collection with one non-null element (the list references itself).
        LinkedList<Object> backingList = new LinkedList<Object>();
        backingList.add((Object) backingList);

        // Index every element to the same constant key (null).
        Transformer<Object, Integer> keyTransformer = ConstantTransformer.nullTransformer();
        IndexedCollection<Integer, Object> indexedCollection =
                IndexedCollection.nonUniqueIndexedCollection((Collection<Object>) backingList, keyTransformer);

        // Predicate matches only null elements, so it matches nothing here.
        Predicate<Object> matchesNullOnly = NullPredicate.nullPredicate();
        boolean wasModified = indexedCollection.removeIf(matchesNullOnly);

        assertFalse("removeIf should report no modification", wasModified);
        assertEquals("the only element should remain", 1, backingList.size());
    }
}
